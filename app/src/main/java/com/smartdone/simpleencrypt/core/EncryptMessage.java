package com.smartdone.simpleencrypt.core;

/**
 * Created by Smartdone on 2016/11/19.
 */
public class EncryptMessage {
    /**
     * 文件总长度
     */
    private long totalLength;
    /**
     * 当前已经加/解密的进度
     */
    private long offset;

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }


}
