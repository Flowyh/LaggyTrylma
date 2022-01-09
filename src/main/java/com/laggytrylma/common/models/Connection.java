package com.laggytrylma.common.models;

public class Connection {
    public Connection(Square near, Square far){
        this.near = near;
        this.far = far;
    };
    public Square near, far;
}
