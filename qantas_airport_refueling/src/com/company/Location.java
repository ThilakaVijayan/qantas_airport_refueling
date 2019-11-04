package com.company;


public class Location implements Cloneable, Comparable<Location> {
    private int x;
    private int y;

    public boolean areNeighbours(Location obj){
        if(this.x==obj.x && Math.abs(this.y-obj.y)==1){
            return true;
        }
        if(this.y==obj.y && Math.abs(this.x-obj.x)==1){
            return true;
        }
        if(this.x+1==obj.x && this.y+1==obj.y){
            return true;
        }
        if(this.x-1==obj.x && this.y-1==obj.y){
            return true;
        }
        if(this.x-1==obj.x && this.y+1 ==obj.y){
            return true;
        }
        if(this.x+1==obj.x && this.y-1==obj.y){
            return true;
        }
        return false;
    }

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Location location = (Location) obj;

        if (this.getX() == location.getX() && this.getY() == location.getY()) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (this.y*10+(9-this.x));
    }

    @Override
    public String toString() {
        return "Location{" +
                this.x +
                ", " + this.y +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Location clone = (Location) super.clone();
        return clone;
    }


    @Override
    public int compareTo(Location location) {
        int xa = this.hashCode();
        int ya = location.hashCode();
        int diff = ya-xa;
        return diff;
    }
}
