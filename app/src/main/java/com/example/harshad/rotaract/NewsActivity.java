package com.example.harshad.rotaract;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


import com.example.harshad.rotaract.Fragment.AdminFragment;
import com.example.harshad.rotaract.Fragment.EventFragment;
import com.example.harshad.rotaract.Fragment.FAQFragment;
import com.example.harshad.rotaract.Fragment.HomeFragment;
import com.example.harshad.rotaract.Fragment.NotificationsFragment;
import com.example.harshad.rotaract.Fragment.Recruitment;
import com.example.harshad.rotaract.Fragment.SponsorFragment;
import com.google.firebase.auth.FirebaseAuth;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class NewsActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    ImageView menu, share;
    // private ZXingScannerView zXingScannerView;
    public static int navItemIndex = 0;

    private View navHeader;

    private static final String TAG_HOME = "home";
    private static final String TAG_EVENT = "event";
    private static final String TAG_RECRUITMENT = "recruitment";
    private static final String TAG_SPONSORS = "sponsor";
//  private static final String TAG_NOTIFICATION = "notification";
    private static final String TAG_ADMIN = "admin";
    //private static final String TAG_SETTINGS = "settings";
    private static final String TAG_SCAN_QR = "scan_qr";
    private static final String TAG_USER_EVENT = "user_events";
    public static String CURRENT_TAG = TAG_HOME;

    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    //Menu me;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Helmet-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        setContentView(R.layout.activity_news);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayShowHomeEnabled(true);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // navHeader = navigationView.getHeaderView(0);
        ImageView scanner = findViewById(R.id.scanner);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        mHandler = new Handler();
        setupNavigationView();
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewsActivity.this, ScanActivity.class));
            }
        });

       /* HomeFragment homeFragment=new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,homeFragment).commit();
*/
  /*      for (int z=0;z<me.size();z++) {
            MenuItem mi = me.getItem(z);
            me.getItem(0).setChecked(true);
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 )
            {
                for (int j=0; j <subMenu.size();j++)
                {
                    MenuItem subMenuItem = subMenu.getItem(j);
                }
            }
        }
*/
  /*      navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        HomeFragment homeFragment=new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,homeFragment).commit();
                        return true;
                    case R.id.nav_events:
                        EventFragment ff = new EventFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, ff).commit();
                        return true;
                    case R.id.nav_sponsors:
                        SponsorFragment ss=new SponsorFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,ss).commit();
                        return true;
                    case R.id.nav_notifications:
                        NotificationsFragment notificationsFragment=new NotificationsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,notificationsFragment).commit();
                        return true;
                    case R.id.nav_settings:
                        SettingsFragment settingsFragment=new SettingsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,settingsFragment).commit();
                        return true;
                    case R.id.nav_recruitment:
                        Recruitment rr=new Recruitment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,rr).commit();
                        return true;
                    case R.id.nav_event_user:
                        FAQFragment fff=new FAQFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,fff).commit();
                        return true;
                    case R.id.admin:
                        intent=new Intent(getApplicationContext(),EventAdd.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_about_us:
                        intent = new Intent(getApplicationContext(),AboutUsActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_action_logout:
                        signOut();
                        return true;
                    default:
                        HomeFragment hh=new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,hh).commit();
                }
                return true;
            }
        });
  */
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void loadHomeFragment() {

        selectNavMenu();

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawerLayout.closeDrawers();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                // fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        //Closing drawer on item click
        drawerLayout.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                HomeFragment hh = new HomeFragment();
                return hh;
            case 1:
                EventFragment ee = new EventFragment();
                return ee;
            case 2:
                Recruitment rr = new Recruitment();
                return rr;
            case 3:
                SponsorFragment sss = new SponsorFragment();
                return sss;
            /*case 4:
                NotificationsFragment notificationsFragment=new NotificationsFragment();
                return notificationsFragment;*/
            case 4:
                FAQFragment ff = new FAQFragment();
                return ff;
            case 5:
                AdminFragment adminFragment = new AdminFragment();
                return adminFragment;
            default:
                return new HomeFragment();
        }
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setupNavigationView() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_events:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_EVENT;
                        break;
                    case R.id.nav_recruitment:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_RECRUITMENT;
                        break;
                    case R.id.nav_sponsors:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_SPONSORS;
                        break;
                    /*case R.id.nav_notifications:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_NOTIFICATION;
                        break;*/
                    case R.id.nav_event_user:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_USER_EVENT;
                        break;
                    /*
                    case R.id.nav_settings:
                        navItemIndex=6;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;
                        */
                    case R.id.admin:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_ADMIN;
                        break;

                    case R.id.nav_about_us:
                        Intent aboutus = new Intent(NewsActivity.this, AboutUsActivity.class);
                        startActivity(aboutus);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_action_logout:
                        signOut();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                item.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(NewsActivity.this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    // private void init() {


    //bottomBar=(BottomBar)findViewById(R.id.bottomBar);
          /*  drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
            final ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close)
            {
                @Override
                public void onDrawerClosed(View drawerView)
                {
                    super.onDrawerClosed(drawerView);
                }
                @Override
                public void onDrawerOpened(View drawerView)
                {
                    super.onDrawerOpened(drawerView);
                }
            };*/

    // menu=(ImageView)findViewById(R.id.navigationViewIcon);

    //  me = navigationView.getMenu();
//        }


    public void signOut() {
        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        Intent intent = new Intent(NewsActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        firebaseAuth.signOut();
        startActivity(intent);
        finish();
    }


   /* public void scan(View view){
        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }
*/

  /*  @Override
    public void handleResult(Result result) {
        Intent intent =new Intent(this,RegistrationForm.class);
        intent.putExtra("event_name",result.getText());
        startActivity(intent);
    }
*/
}

