package com.zhx.weather.listener;

/**
 * 描述：
 */
public interface KeyboardVisibilityEventListener {
    /**
     * @return to remove global listener or not
     */
    boolean onVisibilityChanged(boolean isOpen, int heightDiff);
}
