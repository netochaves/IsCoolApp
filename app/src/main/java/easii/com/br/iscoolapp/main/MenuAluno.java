package easii.com.br.iscoolapp.main;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.fragmentos.DesafioFragment;
import easii.com.br.iscoolapp.fragmentos.FeedFragment;
import easii.com.br.iscoolapp.fragmentos.OneFragment;
import easii.com.br.iscoolapp.fragmentos.OneFragmentAluno;
import easii.com.br.iscoolapp.fragmentos.PerfilFragmentoAluno;
import easii.com.br.iscoolapp.fragmentos.TwoFragment;
import easii.com.br.iscoolapp.fragmentos.TwoFragmentAluno;

public class MenuAluno extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_aluno);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(" IsCool Aluno");
        getSupportActionBar().setLogo(R.drawable.iscool);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.feed);
        tabLayout.getTabAt(1).setIcon(R.drawable.chat1);
        tabLayout.getTabAt(2).setIcon(R.drawable.correcao1);
        tabLayout.getTabAt(3).setIcon(R.drawable.perfil);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FeedFragment(), " Feed");
        adapter.addFragment(new OneFragmentAluno(), " Chats");
        adapter.addFragment(new TwoFragmentAluno(), " Notas");
        adapter.addFragment(new PerfilFragmentoAluno(), " Perfil");
        viewPager.setAdapter(adapter);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_aluno, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {


            Intent i = new Intent(this,Config.class);
            startActivity(i);

         //   Intent i = new Intent(this,TelaExplicacao.class);
          //  startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
