package cn.wufuqi.nbeffects.helper;

import java.util.LinkedList;

/**
 * 若在某些条件下才能运行的一些行为，提前运行则失去了意义，所以此时可以将这些事件保存起来，等条件满足之后再全部按顺序运行
 */
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