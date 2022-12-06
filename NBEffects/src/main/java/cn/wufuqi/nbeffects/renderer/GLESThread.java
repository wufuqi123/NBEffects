package cn.wufuqi.nbeffects.renderer;

import android.graphics.SurfaceTexture;
import android.opengl.GLUtils;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

import cn.wufuqi.nbeffects.helper.PendingThreadAider;

public class GLESThread extends Thread {
    private static final String TAG = "GLESTVThread";
    private SurfaceTexture mSurfaceTexture;
    private EGL10 mEgl;
    private EGLDisplay mEglDisplay = EGL10.EGL_NO_DISPLAY;// 显示设备
    private EGLSurface mEglSurface = EGL10.EGL_NO_SURFACE;
    private EGLContext mEglContext = EGL10.EGL_NO_CONTEXT;
    //    private GL mGL;
    private static final int EGL_CONTEXT_CLIENT_VERSION = 0x3098;
    private static final int EGL_OPENGL_ES2_BIT = 4;
    private FilterRenderer mRenderer;
    private PendingThreadAider mPendingThreadAider = new PendingThreadAider();
    private boolean mNeedRenderring = true;
    private Object LOCK = new Object();
    private boolean mIsPaused = false;

    private ArrayList<Runnable> mEventQueue = new ArrayList<Runnable>();

    public GLESThread(SurfaceTexture surface, FilterRenderer renderer) {
        mSurfaceTexture = surface;
        mRenderer = renderer;
    }

    @Override
    public void run() {

        initGLESContext();
        mRenderer.onSurfaceCreated(null, null);
        while (mNeedRenderring) {

            while (!mEventQueue.isEmpty()) {
                Runnable event = mEventQueue.remove(0);
                if (event != null) {
                    event.run();
                }
            }

            mPendingThreadAider.runPendings();
            mRenderer.onDrawFrame(null);
            mEgl.eglSwapBuffers(mEglDisplay, mEglSurface);
            if (mIsPaused) {
                pauseWhile();
            } else if (mRendererMode == NBTextureView.RENDERMODE_WHEN_DIRTY) {
                pauseWhile();
            }


        }
        destoryGLESContext();
    }

    private void initGLESContext() {

        mEgl = (EGL10) EGLContext.getEGL();
        mEglDisplay = mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);// 获取显示设备
        if (mEglDisplay == EGL10.EGL_NO_DISPLAY) {
            throw new RuntimeException("eglGetdisplay failed : " + GLUtils.getEGLErrorString(mEgl.eglGetError()));
        }

        int[] version = new int[2];
        if (!mEgl.eglInitialize(mEglDisplay, version)) {
            throw new RuntimeException("eglInitialize failed : " + GLUtils.getEGLErrorString(mEgl.eglGetError()));
        }

        int[] configAttribs = { //
                EGL10.EGL_BUFFER_SIZE, 32,
                EGL10.EGL_ALPHA_SIZE, 8,
                EGL10.EGL_BLUE_SIZE, 8,
                EGL10.EGL_GREEN_SIZE, 8,
                EGL10.EGL_RED_SIZE, 8,
                EGL10.EGL_RENDERABLE_TYPE, EGL_OPENGL_ES2_BIT,
                EGL10.EGL_SURFACE_TYPE, EGL10.EGL_WINDOW_BIT, EGL10.EGL_NONE
        };

        int[] numConfigs = new int[1];
        EGLConfig[] configs = new EGLConfig[1];
        if (!mEgl.eglChooseConfig(mEglDisplay, configAttribs, configs, 1, numConfigs)) {
            throw new RuntimeException("eglChooseConfig failed : " + GLUtils.getEGLErrorString(mEgl.eglGetError()));
        }

        int[] contextAttribs = {EGL_CONTEXT_CLIENT_VERSION, 2, EGL10.EGL_NONE};
        mEglContext = mEgl.eglCreateContext(mEglDisplay, configs[0], EGL10.EGL_NO_CONTEXT,
                contextAttribs);
        mEglSurface = mEgl.eglCreateWindowSurface(mEglDisplay, configs[0], mSurfaceTexture,
                null
        );
        if (mEglSurface == EGL10.EGL_NO_SURFACE || mEglContext == EGL10.EGL_NO_CONTEXT) {
            int error = mEgl.eglGetError();
            if (error == EGL10.EGL_BAD_NATIVE_WINDOW) {
                throw new RuntimeException("eglCreateWindowSurface returned  EGL_BAD_NATIVE_WINDOW. ");
            }
            throw new RuntimeException("eglCreateWindowSurface failed : " + GLUtils.getEGLErrorString(mEgl.eglGetError()));
        }

        if (!mEgl.eglMakeCurrent(mEglDisplay, mEglSurface, mEglSurface, mEglContext)) {
            throw new RuntimeException("eglMakeCurrent failed : " + GLUtils.getEGLErrorString(mEgl.eglGetError()));
        }
//        mGL = mEglContext.getGL();
    }

    private void pauseWhile() {
        synchronized (LOCK) {
            try {
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void destoryGLESContext() {
        mEgl.eglDestroyContext(mEglDisplay, mEglContext);
        mEgl.eglDestroySurface(mEglDisplay, mEglSurface);
        mEglContext = EGL10.EGL_NO_CONTEXT;
        mEglSurface = EGL10.EGL_NO_SURFACE;
    }

    public void onPause() {
//        mRenderer.onPause();
        mIsPaused = true;
    }

    public void onResume() {
//        mRenderer.onResume();
        mIsPaused = false;
        requestRender();
    }

    public void onSurfaceChanged(final int width, final int height) {
        mPendingThreadAider.addToPending(new Runnable() {

            @Override
            public void run() {
                mRenderer.onSurfaceChanged(null, width, height);
            }
        });
    }

    private int mRendererMode = NBTextureView.RENDERMODE_CONTINUOUSLY;

    public void setRenderMode(int mode) {
        mRendererMode = mode;
    }

    public void requestRender() {
        synchronized (LOCK) {
            LOCK.notifyAll();
        }
    }

    public void onDestroy() {
        mNeedRenderring = false;
        mRenderer.destroy();
        destoryGLESContext();
    }

    public void queueEvent(Runnable r) {
        if (r == null) {
            throw new IllegalArgumentException("r must not be null");
        }
        synchronized (LOCK) {
            mEventQueue.add(r);
            LOCK.notifyAll();
        }
    }
}