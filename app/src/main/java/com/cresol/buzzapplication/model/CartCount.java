package com.cresol.buzzapplication.model;

/**
 * Created by Saurabh on 9/22/2016.
 */
//interface to transfer data that we are saving in list i.e dishId by which we identify uniquely
public interface CartCount {
    public void transferCountToCart(Integer cartCount);

    public void ShowCartCount();
}