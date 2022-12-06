package cn.wufuqi.nbeffects.helper;

import java.util.LinkedList;


public class PendingThreadAider {
    LinkedList<Runnable> mRunOnDraw = new LinkedList<Runnable>();

    public void runPendings() {
        while (!mRunOnDraw.isEmpty()) {
            mRunOnDraw.removeFirst().run();
        }
    }

    public void addToPending(final Runnable runnable) {
        synchronized (mRunOnDraw) {
            mRunOnDraw.addLast(runnable);
        }
    }
}