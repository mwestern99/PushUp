package com.example.elizabeth.pushup;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.elizabeth.pushup.R.id.BlockView;
import static com.example.elizabeth.pushup.R.id.MusicListView;

public class Library extends AppCompatActivity
{

    List<AppFunction> FunctionList;
    GridView gridView;
    ImageButton BlockPlayButton = null;
    MediaPlayer mediaPlayer;
    int RandomSong=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        gridView = (GridView) findViewById(R.id.grid);
        setData();
        setGridView();
        SongsList();
    }

    //set rollbar data
    private void setData()
    {
        FunctionList = new ArrayList<AppFunction>();
        AppFunction function = new AppFunction();
        function.setFunctionName("Media"+" "+"File");
        FunctionList.add(function);
        function = new AppFunction();
        function.setFunctionName("HeartBeat"+" "+"Type");
        FunctionList.add(function);
        function = new AppFunction();
        function.setFunctionName("Artist");
        FunctionList.add(function);
    }

    //set GridView Format data
    private void setGridView() {
        int size = FunctionList.size();
        int length = 130;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        int itemWidth = (int) (length * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gridView.setLayoutParams(params); //set GirdView Layout
        gridView.setColumnWidth(itemWidth); // set column width
        gridView.setHorizontalSpacing(5); // set spacing
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size); //set the num of column = the number of "function" number

        GridViewAdapter adapter = new GridViewAdapter(getApplicationContext(), FunctionList);
        gridView.setAdapter(adapter);
    }

    //set GridView Adapter
    public class GridViewAdapter extends BaseAdapter {
        Context context;
        List<AppFunction> list;
        public GridViewAdapter(Context _context, List<AppFunction> _list) {
            this.list = _list;
            this.context = _context;
        }

        @Override
        public int getCount()
        {
            return list.size();
        }

        @Override
        public Object getItem(int position)
        {
            return list.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.roll_list_item, null);
            TextView tvCity = (TextView) convertView.findViewById(R.id.function);
            AppFunction city = list.get(position);

            tvCity.setText(city.getFunctionName());
            return convertView;
        }
    }

    public class AppFunction
    {
        private String FunctionName;

        public String getFunctionName()
        {
            return FunctionName;
        }

        public void setFunctionName(String FunctionName)
        {
            this.FunctionName = FunctionName;
        }

    }


    public void SongsList()
    {

        ListView list =(ListView) findViewById(MusicListView);

        final ArrayList<String> musicList = new ArrayList<>();

        final Field[] fields = R.raw.class.getFields();

        for(int i = 0;i<fields.length;i++)
        {
            musicList.add(fields[i].getName());
        }
        //remove first two element
        musicList.remove(0);
        musicList.remove(0);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1,musicList);

        list.setAdapter(adapter);


        String sblockInfo;
        Random rnd = new Random();
        while (RandomSong==0||RandomSong==1)
        {
            RandomSong=rnd.nextInt(fields.length);
        }
        sblockInfo=fields[RandomSong].getName();
        TextView blockInfo =(TextView) findViewById(BlockView);
        blockInfo.setText(sblockInfo);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id)
            {
                int ClickListID=getResources().getIdentifier(musicList.get(position),"raw",getPackageName());
                mediaPlayer=MediaPlayer.create(Library.this,ClickListID);
                mediaPlayer.start();
            }
        });

        BlockPlayButton = (ImageButton) findViewById(R.id.BPlayButton);
        BlockPlayButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                int resID=getResources().getIdentifier(musicList.get(RandomSong),"raw",getPackageName());
                mediaPlayer= MediaPlayer.create(Library.this,resID);
                mediaPlayer.start();
            }
        });

    }


}
