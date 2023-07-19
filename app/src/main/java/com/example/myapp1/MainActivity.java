package com.example.myapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // increment button +1
    public void increment(View view) {
        quantity+=1;
        display_quantity(quantity, (TextView) findViewById(R.id.quantity_text_view2));
    }
    // decrement button -1
    public void decrement(View view) {
        if(quantity>0) quantity-=1;    // 購買數量不能<0
        display_quantity(quantity, (TextView) findViewById(R.id.quantity_text_view2));
    }

    private int caculate_price(){
        return quantity*90;
    }

    private String createOrderMessage(String customerName, boolean hasPlasticBag, int price){
        String orderMessage="";
        orderMessage = orderMessage+"Customer Name: "+customerName+"\n";
        orderMessage = orderMessage+"add plastic bag? "+hasPlasticBag+"\n";
        orderMessage = orderMessage+"quantity: "+String.valueOf(quantity)+"\n";
        orderMessage = orderMessage+"Total price: "+String.valueOf(price)+"\n";
        orderMessage+="Thank You~\n";
        return orderMessage;
    }

    public void submitOrder(View view){
        // 用一個CheckBox型別的變數儲存CheckBox
        CheckBox plasticBagCheckBox = findViewById(R.id.addPlasticBag_checkbox);
        boolean hasPlasticBag = plasticBagCheckBox.isChecked();
        //計算總價
        int price = caculate_price();
        // 查看checkbox狀態設定
        if(hasPlasticBag==true)price += 2;
        // 取得使用者輸入姓名
        EditText customerNameInput = findViewById(R.id.input_name);
        String customerName = customerNameInput.getText().toString();
        // create 總訂單資訊字串
        String orderMessage = createOrderMessage(customerName, hasPlasticBag, price);
        // display總訂單資訊
        displayOrderMessage(orderMessage, (TextView) findViewById(R.id.summary_text_view4));
        // email intent(開不起來)
        /*
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:test@gmail.com"));// only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,  customerName + "先生/小姐 的訂單"); //主旨
        intent.putExtra(Intent.EXTRA_TEXT,  orderMessage); //內容
        if (intent.resolveActivity(getPackageManager()) != null){
            Log.i("MainActivity", "Yes");
            startActivity(intent);
        }*/

        // 開啟second activity
        Intent i = new Intent(getApplicationContext(), secondActivity.class);
        startActivity(i);//播完之後就結束了，怎麼讓它可以播完載波一次？(最後好像升級完一些軟體就解決了)
    }

    private void display_quantity(int number, TextView NumberTextView) {
        NumberTextView.setText("購買數量： " + number);
    }

    private void displayOrderMessage(String orderMessage, TextView summaryTextView) {
        summaryTextView.setText("" + orderMessage);
    }

}