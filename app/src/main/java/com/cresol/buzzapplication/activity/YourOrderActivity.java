package com.cresol.buzzapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.adapter.YourOrderAdapter;
import com.cresol.buzzapplication.api.api;
import com.cresol.buzzapplication.drawer.DrawerActivity;
import com.cresol.buzzapplication.model.CartOrderModel;
import com.cresol.buzzapplication.model.OrderDishList;
import com.cresol.buzzapplication.model.OrderOutput;
import com.cresol.buzzapplication.model.YourOrderModel;
import com.cresol.buzzapplication.util.Constants;
import com.cresol.buzzapplication.util.GlobalMethods;
import com.cresol.buzzapplication.util.UserSessionManager;
import com.payu.india.Model.PaymentParams;
import com.payu.india.Model.PayuConfig;
import com.payu.india.Model.PayuHashes;
import com.payu.india.Payu.PayuConstants;
import com.sasidhar.smaps.payu.PaymentOptions;
import com.sasidhar.smaps.payu.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Saurabh on 8/1/2016.
 */
public class YourOrderActivity extends DrawerActivity {

    public float dishOrderPrice;

    EditText tableNumber;
    EditText numberOfGuest;
    UserSessionManager sessionManager;
    TextView vatPrice;
    TextView serviceCharge;
    TextView serviceTax;
    TextView swatchBharatCess;
    private PaymentParams paymentParams = new PaymentParams();
    private PayuConfig payuConfig = new PayuConfig();
    TextView serviceChargeTextView;
    TableLayout serviceLayout;
    GridView gridViewref;


    List<OrderDishList> orderDishLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_your_order, null, false);
        drawer.addView(contentView, 0);

        GlobalMethods.overrideFonts(this, contentView);

        showCartCountOnCart();

        sessionManager = new UserSessionManager(this);
        numberOfGuest = (EditText) findViewById(R.id.number_guest_price_id);
        tableNumber = (EditText) findViewById(R.id.table_number_price_id);

        vatPrice = (TextView) findViewById(R.id.vat_price_id);
        serviceCharge = (TextView) findViewById(R.id.service_charge_price_id);
        serviceTax = (TextView) findViewById(R.id.service_tax_price_id);
        swatchBharatCess = (TextView) findViewById(R.id.swach_bharat_price_id);
        serviceChargeTextView = (TextView) findViewById(R.id.serviceChargeTextView);
        serviceLayout = (TableLayout) findViewById(R.id.serviceLayout);
        gridViewref = (GridView) findViewById(R.id.grd_product);
       // gridViewref.setVisibility(View.GONE);

        //  serviceChargeTextView.setOnClickListener(new View.OnClickListener() {
        //   @Override
        //  public void onClick(View v) {
        //       if(serviceLayout.getVisibility()==View.VISIBLE){
        //           serviceLayout.setVisibility(View.GONE);

        //   }
        //    else{
        //        serviceLayout.setVisibility(View.VISIBLE);

        //  }

        //serviceLayout.animate().translationY(20);
        // }
        // });

        List<CartOrderModel> cartOrderModels = new ArrayList<>();
        //get the INformation of the saved dish list in the sqlite
        cartOrderModels = GlobalMethods.GetInformation(this);


        for (CartOrderModel obj : cartOrderModels) {
            OrderDishList orderDishListsObj = new OrderDishList();
            orderDishListsObj.setItemID(Integer.valueOf(obj.getDish_Id()));
            orderDishListsObj.setPrice(Float.valueOf(obj.getDish_Price()));
            orderDishListsObj.setQuantity(Integer.valueOf(obj.getDish_Quantity()));

            orderDishLists.add(orderDishListsObj);
            dishOrderPrice = dishOrderPrice + (Integer.valueOf(obj.getDish_Quantity()) * Float.parseFloat(obj.getDish_Price()));
        }

        TextView totalPrice = (TextView) findViewById(R.id.total_amount_price_id);
        totalPrice.setText(String.valueOf(dishOrderPrice));

        YourOrderAdapter adapter = new YourOrderAdapter(YourOrderActivity.this, R.layout.your_order_child_list, cartOrderModels);
        gridViewref.setVisibility(View.VISIBLE);
        gridViewref.setAdapter(adapter);
    }

    public void showCartCountOnCart() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_drawer_id);
        setSupportActionBar(toolbar);
        TextView toolbarCartTxt = (TextView) findViewById(R.id.toolbar_cart_text);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.toolbar_image_layout);

        int count = GlobalMethods.GetCount(this);
        toolbarCartTxt.setText(String.valueOf(count));

        //Remove apptitle from Toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), YourOrderActivity.class);
                startActivity(i);
            }
        });
    }

    public void split(View v) {
        Intent i = new Intent(this, SplitBillActivity.class);
        startActivity(i);
    }


    public void RetroCallForPostOrder(View v) {
        //Login
        if (sessionManager.isUserLoggedIn()) {

        //Condition for checking the date number of guest and table numbwr

        if (numberOfGuest.getText().toString().isEmpty() || tableNumber.getText().toString().isEmpty()) {

            Toast.makeText(YourOrderActivity.this, "please fill table number and number of guest", Toast.LENGTH_LONG).show();

        } else {
            //This condition for checking internet
            if (GlobalMethods.isNetworkAvailable(this)) {


                api buzzapi = GlobalMethods.GetBuzzdAPI(this);

                YourOrderModel obj = new YourOrderModel();
                obj.setUserID(3);
                obj.setDish(orderDishLists);
                obj.setNumberOfGuests(Integer.valueOf(numberOfGuest.getText().toString()));
                obj.setServiceCharge(Float.parseFloat(serviceCharge.getText().toString()));
                obj.setServiceTax(Float.parseFloat(serviceTax.getText().toString()));
                obj.setSwatchBharatCess(Float.parseFloat(swatchBharatCess.getText().toString()));
                obj.setVatTax(Float.parseFloat(vatPrice.getText().toString()));

                obj.setTableNumber(Integer.valueOf(tableNumber.getText().toString()));

                GlobalMethods.ShowProgressBar(this);
                Call<OrderOutput> orderOutputCall = buzzapi.PostOrder(obj);

                orderOutputCall.enqueue(new Callback<OrderOutput>() {
                    @Override
                    public void onResponse(Call<OrderOutput> call, Response<OrderOutput> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Intent i = new Intent(getApplicationContext(), PaymentActivity.class);
                            i.putExtra("orderId", String.valueOf(response.body().getOrderID()));
                            i.putExtra("totalAmount", String.valueOf(dishOrderPrice));
                            startActivity(i);
                            //   PaymentCall();
                            Toast.makeText(YourOrderActivity.this, "your order received.your order id is" + String.valueOf(response.body().getOrderID()), Toast.LENGTH_SHORT).show();
                        }
                        GlobalMethods.HideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<OrderOutput> call, Throwable t) {
                        GlobalMethods.HideProgressBar();
                    }
                });
            }
        }
         }
        else {
          Toast.makeText(YourOrderActivity.this, "You are not Login", Toast.LENGTH_LONG).show();
        }
    }


    public void PaymentCall() {

        payuConfig.setEnvironment(Constants.ENV);


        paymentParams.setKey(Constants.MERCHANT_KEY);
        paymentParams.setFirstName("Nitesh");
        paymentParams.setEmail("email@gmail.com ");
        paymentParams.setPhone("8462039227");
        paymentParams.setProductInfo("testing");
        paymentParams.setAmount("90");
        paymentParams.setTxnId("" + System.currentTimeMillis());
        paymentParams.setSurl(Constants.SURL);
        paymentParams.setFurl(Constants.FURL);
        paymentParams.setUdf1("");
        paymentParams.setUdf2("");
        paymentParams.setUdf3("");
        paymentParams.setUdf4("");
        paymentParams.setUdf5("");

        PayuHashes payuHashes = Utils.generateHashFromSDK(paymentParams, Constants.SALT);
        paymentParams.setHash(payuHashes.getPaymentHash());

        PaymentOptions.isEMIEnabled = false;

        Intent intent = new Intent(this, com.sasidhar.smaps.payu.PaymentActivity.class);
        intent.putExtra(PayuConstants.PAYU_CONFIG, payuConfig);
        intent.putExtra(PayuConstants.PAYMENT_PARAMS, paymentParams);
        intent.putExtra(PayuConstants.PAYU_HASHES, payuHashes);

        startActivityForResult(intent, PayuConstants.PAYU_REQUEST_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PayuConstants.PAYU_REQUEST_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    Toast.makeText(YourOrderActivity.this, "Payment Success.", Toast.LENGTH_SHORT).show();
                    break;

                case RESULT_CANCELED:
                    Toast.makeText(YourOrderActivity.this, "Payment Cancelled | Failed.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
