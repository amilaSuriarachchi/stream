package edu.colostate.cs.worker.comm.server;

import edu.colostate.cs.worker.comm.exception.MessageProcessingException;
import edu.colostate.cs.worker.config.Configurator;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: amila
 * Date: 3/19/14
 * Time: 11:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class DataReader extends InputStream {

    // these represents the mode that buffer in.
    public static final int BUFFER_READ_MODE = 1;
    public static final int BUFFER_WRITE_MODE = 2;

    private Lock lock;
    private Condition condition;
    private volatile int mode;

    private volatile boolean start;

    private ByteBuffer byteBuffer;

    private DataInput dataInput;


    public DataReader() {
        this.lock = new ReentrantLock();
        this.condition = this.lock.newCondition();
        this.mode = BUFFER_WRITE_MODE;
        this.start = true;
        this.byteBuffer = ByteBuffer.allocate(Configurator.getInstance().getByteBufferSize());
        this.dataInput = new DataInputStream(this);
    }

    @Override
    public int read() throws IOException {
        this.lock.lock();
        setReadMode();

        try {
            if (!this.byteBuffer.hasRemaining()) {
                try {
                    this.condition.await();
                } catch (InterruptedException e) {
                    //TODO : handle this properly
                }
            }
            setReadMode();
            int value = this.byteBuffer.get() & 0xFF;
            return value;
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        this.lock.lock();
        setReadMode();

        try {
            if (!this.byteBuffer.hasRemaining()) {
                try {
                    this.condition.await();
                } catch (InterruptedException e) {
                    //TODO : handle this properly
                }
            }
            setReadMode();
            int chunk = Math.min(this.byteBuffer.remaining(), len);
            this.byteBuffer.get(b, off, chunk);
            return chunk;
        } finally {
            this.lock.unlock();
        }

    }

    public void readReady(SelectionKey selectionKey) throws MessageProcessingException {
        this.lock.lock();
        try {
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            setWriteMode();
            socketChannel.read(this.byteBuffer);
            this.condition.signalAll();

        } catch (IOException e) {
            throw new MessageProcessingException("Can not write to the buffer", e);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void close() throws IOException {
        this.start = true;
    }

    private void setWriteMode() {
        if (this.mode == BUFFER_READ_MODE) {
            if (this.byteBuffer.hasRemaining()) {
                this.byteBuffer.compact();
            } else {
                this.byteBuffer.clear();
            }
            this.mode = BUFFER_WRITE_MODE;
        }
    }

    private void setReadMode() {
        if (this.mode == BUFFER_WRITE_MODE) {
            this.byteBuffer.flip();
            this.mode = BUFFER_READ_MODE;
        }
    }

    @Override
    public int available() throws IOException {
        this.lock.lock();
        try {
           setReadMode();
           return this.byteBuffer.limit() - this.byteBuffer.position();
        } finally {
            this.lock.unlock();
        }

    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public DataInput getDataInput() {
        return dataInput;
    }


}