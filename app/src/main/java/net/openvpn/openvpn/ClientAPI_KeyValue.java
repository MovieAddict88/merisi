package net.openvpn.openvpn;

public class ClientAPI_KeyValue {
    protected boolean swigCMemOwn;
    private long swigCPtr;

    protected ClientAPI_KeyValue(long cPtr, boolean cMemoryOwn) {
        this.swigCMemOwn = cMemoryOwn;
        this.swigCPtr = cPtr;
    }

    protected static long getCPtr(ClientAPI_KeyValue obj) {
        return obj == null ? 0 : obj.swigCPtr;
    }

    protected void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ovpncliJNI.delete_ClientAPI_KeyValue(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public void setKey(String value) {
        ovpncliJNI.ClientAPI_KeyValue_key_set(this.swigCPtr, this, value);
    }

    public String getKey() {
        return ovpncliJNI.ClientAPI_KeyValue_key_get(this.swigCPtr, this);
    }

    public void setValue(String value) {
        ovpncliJNI.ClientAPI_KeyValue_value_set(this.swigCPtr, this, value);
    }

    public String getValue() {
        return ovpncliJNI.ClientAPI_KeyValue_value_get(this.swigCPtr, this);
    }

    public ClientAPI_KeyValue() {
        this(ovpncliJNI.new_ClientAPI_KeyValue(), true);
    }
}
