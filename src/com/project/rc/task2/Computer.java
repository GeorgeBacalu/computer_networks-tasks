package com.project.rc.task2;

import java.util.Objects;

public class Computer {
    private String buffer;
    private String ipAddress;
    private String data;

    public Computer(String buffer, String ipAddress) {
        this.buffer = buffer;
        this.ipAddress = ipAddress;
        this.data = null;
    }

    public String getBuffer() {
        return buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return buffer + "(" + ipAddress + ") -> " + data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Computer computer = (Computer) o;
        return Objects.equals(buffer, computer.buffer) &&
              Objects.equals(ipAddress, computer.ipAddress) &&
              Objects.equals(data, computer.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buffer, ipAddress, data);
    }
}
