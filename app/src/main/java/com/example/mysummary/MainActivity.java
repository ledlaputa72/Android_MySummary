package com.example.mysummary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //좋아요용 변수
    TextView likeCountView;
    Button likeButton;
    int likeCount = 1;
    boolean likeState = false;

    //한줄평용 리스트 뷰에 사용하는 어뎁터
    CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //좋아요 버튼 메소드
        likeButton = (Button) findViewById(R.id.likeButton);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(likeState) {
                    decrLikeCount();
                } else {
                    incrLikeCount();
                }
                likeState = !likeState;
            }
        });

        likeCountView = (TextView) findViewById(R.id.likeCountView);


        //한줄평 리스트 뷰 어뎁터 구현
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new CommentAdapter();

        //기본 출력되는 내용(데이터)
        adapter.addComment(new CommentItem(R.drawable.user1,"kym71**","20분전","적당히 재밌다. 오랜만에 잠 안오는 영화 봤네요.^^"));
        adapter.addComment(new CommentItem(R.drawable.user1,"love22**","10분전","하정우 너무 멋져요"));

        listView.setAdapter(adapter);

        //listView를 클릭할때 이벤트 발생 (토스트 메시지로 유저 ID(이름) 출력)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommentItem item = (CommentItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 : "+ item.getUserName(),Toast.LENGTH_SHORT).show();
            }
        });

        //작성하기 버튼을 눌러 리스트뷰 내용 추가하기 - 버튼으로
        /*Button button = (Button) findViewById(R.id.listViewButton);
        button.setOnClickListener(new View.OnClickListener() {*/

        //텍스트 뷰로 버튼 기능하기
        TextView button = (TextView) findViewById(R.id.listViewButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //추가되는 내용
                adapter.addComment(new CommentItem(R.drawable.user1,"new**","1분전","감상평입니다. 굿굿굿"));
                adapter.notifyDataSetChanged();
            }
        });

    }

    //한줄평 CommentAdapter 메소드
    class CommentAdapter extends BaseAdapter {

        ArrayList<CommentItem> comments = new ArrayList<CommentItem>();

        @Override
        public int getCount() {
            //리스트 안에 몇개의 한줄평이 들어가는지 count로 알아본다.
            return comments.size();
        }

        //view 안에 데이터가 없다. 데이터를 추가하는 메소드
        public void addComment(CommentItem comment){

            comments.add(comment);
        }

        @Override
        public Object getItem(int position) {
            return comments.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //CommentItemView view = new CommentItemView(getApplicationContext());

            CommentItemView view = null;

            if(convertView == null ) {
                view = new CommentItemView(getApplicationContext());
            } else {
                view = (CommentItemView) convertView;
            }

            CommentItem comment = comments.get(position);
            view.setUserId(comment.getUserId());
            view.setUserName(comment.getUserName());
            view.setTime(comment.getTime());
            view.setCommentText(comment.getCommentText());

            return view;
        }
    }

    //좋아요 메소드 /////////////////////
    //눌렀을때 값 추가와 이미지 변화
    public void incrLikeCount() {
        likeCount += 1;
        likeCountView.setText(String.valueOf(likeCount)); //likeCount가 int형이므로 String의로 형변화
        likeButton.setBackgroundResource(R.drawable.ic_thumb_up_selected); //눌린 이미지로 변경
    }

    //눌렀을때 값의 추가와 이미지 변화
    public void decrLikeCount() {
        likeCount -= 1;
        likeCountView.setText(String.valueOf(likeCount));
        likeButton.setBackgroundResource(R.drawable.thumb_up_selector);
    }
}

