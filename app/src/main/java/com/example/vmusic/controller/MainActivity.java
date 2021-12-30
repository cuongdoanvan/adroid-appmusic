package com.example.vmusic.controller;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vmusic.R;
import com.example.vmusic.fragment.FragmentDanhSachYeuThich;
import com.example.vmusic.fragment.FragmentTimKiem;
import com.example.vmusic.fragment.FragmentTrangChu;
import com.example.vmusic.fragment.FramentNull;
import com.example.vmusic.interfaceClass.InterfaceCheckingConnection;

public class MainActivity extends AppCompatActivity {

    DrawerLayout layoutMain;
    Toolbar toolbar;
    FrameLayout frameMain;
    FragmentManager fragmentManager;
    TextView txtNavigationTrangChu, txtNavigationYeuThich;

    EditText txtENavigationTimKiem;
    ImageButton imgNavigationTimKiem;
    FrameLayout frameNavigation;

    String dataSend ="";


    CheckNetwork broadcasReceiver = new CheckNetwork(new InterfaceCheckingConnection() {
        @Override
        public void checkNetword(Boolean ck) {
            if(ck){
                Toast.makeText(MainActivity.this,"Lỗi kết nối",Toast.LENGTH_SHORT).show();
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcasReceiver,filter);

    }

    private void addControls() {
        layoutMain = findViewById(R.id.layout_main);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("VMusic");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                layoutMain,
                toolbar,
                R.string.open_navigation,
                R.string.close_navigation
        );
        layoutMain.addDrawerListener(toggle);
        toggle.syncState();
        frameMain = findViewById(R.id.frame_main);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_main, FragmentTrangChu.class,null)
                .addToBackStack("one")
                .commit();
        txtNavigationTrangChu = findViewById(R.id.txt_navigation_trang_chu);
        txtNavigationYeuThich = findViewById(R.id.txt_navigation_danh_sach_yeu_thich);

        txtENavigationTimKiem = findViewById(R.id.txt_e_navigation_tim_kiem);
        imgNavigationTimKiem = findViewById(R.id.img_navigation_timkiem);
        frameNavigation = findViewById(R.id.frame_navigation);
    }

    private void addEvents() {
        txtNavigationTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTrangChu();
            }
        });
        txtNavigationYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDanhSachYeuThich();
            }
        });

        imgNavigationTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButronTimKiem();
            }
        });
    }

    private void clickButronTimKiem() {
        dataSend = txtENavigationTimKiem.getText().toString();
        txtENavigationTimKiem.onEditorAction(EditorInfo.IME_ACTION_DONE);
        fragmentManager.beginTransaction()
                .replace(R.id.frame_navigation, FragmentTimKiem.class,null)
                .commit();
    }

    public String getDataSend() {
        return dataSend;
    }

    private void clickDanhSachYeuThich() {
        layoutMain.closeDrawer(GravityCompat.START);
        txtNavigationYeuThich.setBackgroundResource(R.color.navigation);
        txtNavigationTrangChu.setBackgroundResource(R.color.delete_navigation);
        fragmentManager.beginTransaction()
                .replace(R.id.frame_main, FragmentDanhSachYeuThich.class,null)
                .addToBackStack("one")
                .commit();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_navigation, FramentNull.class,null)
                .commit();
    }

    private void clickTrangChu() {
        layoutMain.closeDrawer(GravityCompat.START);
        txtNavigationYeuThich.setBackgroundResource(R.color.delete_navigation);
        txtNavigationTrangChu.setBackgroundResource(R.color.navigation);
        fragmentManager.beginTransaction()
                .replace(R.id.frame_main, FragmentTrangChu.class,null)
                .addToBackStack("one")
                .setReorderingAllowed(true)
                .commit();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_navigation, FramentNull.class,null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if(layoutMain.isDrawerOpen(GravityCompat.START)){
            layoutMain.closeDrawer(GravityCompat.START);
        }else if(getSupportFragmentManager().getBackStackEntryCount()>0){
            getSupportFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }
    public void cloceNavigation(){
        if(layoutMain.isDrawerOpen(GravityCompat.START)){
            layoutMain.closeDrawer(GravityCompat.START);
        }
    }

}