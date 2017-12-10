package com.jiangdg.poidemos.view;

import android.graphics.Typeface;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jiangdg.poidemos.R;
import com.jiangdg.poidemos.bean.word.ParseResultBean;
import com.jiangdg.poidemos.bean.word.WordCharRunBean;
import com.jiangdg.poidemos.presenter.MainPresenter;
import com.jiangdg.poidemos.utils.SpannableStringUtil;
import com.jiangdg.poidemos.utils.WordReadUtil2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jiangdg.poidemos.utils.Contants.boldBegin;
import static com.jiangdg.poidemos.utils.Contants.boldEnd;
import static com.jiangdg.poidemos.utils.Contants.htmlBegin;
import static com.jiangdg.poidemos.utils.Contants.htmlEnd;
import static com.jiangdg.poidemos.utils.Contants.italicBegin;
import static com.jiangdg.poidemos.utils.Contants.italicEnd;
import static com.jiangdg.poidemos.utils.Contants.underlineBegin;
import static com.jiangdg.poidemos.utils.Contants.underlineEnd;

/**
 * 笔记：
 * XWPFWordExtractor
 * XWPFDocument
 */
public class PoiMainActivity extends AppCompatActivity implements IMainView {
    @BindView(R.id.btn_read_word)
    Button mBtnReadDoc;
    @BindView(R.id.tv_result)
    TextView mTvResult;

    private MainPresenter mPresenter;
    String wordPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
            File.separator + "test1.docx";
    private FileOutputStream output;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            List<SpannableString> stringList = (List<SpannableString>) msg.obj;
            SpannableStringBuilder sb = new SpannableStringBuilder();
            for (SpannableString string : stringList) {
                if(string==null || string.length()==0)
                    continue;
                // 一条文献开始
                if (isReferenceTag(string.toString())) {
                    sb.append("\n");
                }
                sb.append(string);
            }
            mTvResult.setText(sb);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mPresenter = new MainPresenter(this);
    }

    @OnClick({R.id.btn_read_word})
    public void onViewClick(View view) {
        int vId = view.getId();
        switch (vId) {
            case R.id.btn_read_word:
                if (mPresenter != null) {
                    mPresenter.fetchData();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public String getDocumentPath() {

        return wordPath;
    }

    @Override
    public void onParseResult(ParseResultBean result) {
        Message msg = new Message();
        msg.what = 0x00;
        msg.obj = result.getSpanStrings();
        mHandler.sendMessage(msg);
    }

//    @Override
//    public void onParseResult(List<WordCharRunBean> results) {
//
//        String htmlPath = Environment.getExternalStorageDirectory() + "/test.html";
//        File file = new File(htmlPath);
//        if (file.exists()) {
//            file.delete();
//        }
//        try {
//            // 写html文件头部
//            output = new FileOutputStream(new File(htmlPath));
//            output.write(htmlBegin.getBytes());
//            // 解析数据，写到html文件中
//            writeToHtml(results);
//            // 写html文件尾部
//            output.write(htmlEnd.getBytes());
//            output.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    private void writeToHtml(List<WordCharRunBean> results) {
        StringBuilder sb = new StringBuilder();
        for (WordCharRunBean bean : results) {
            if (bean == null || bean.getText() == null)
                continue;

            String text = bean.getText();
            try {
                byte[] boldBegin = bean.getBoldBegin();
                if (boldBegin!=null && boldBegin.length != 0) {
                    output.write(boldBegin);
                }
                byte[] italicBegin = bean.getItalicBegin();
                if (italicBegin!=null && italicBegin.length != 0) {
                    output.write(italicBegin);
                }
                byte[] underlineBegin = bean.getUnderlineBegin();
                if (underlineBegin!=null && underlineBegin.length != 0) {
                    output.write(underlineBegin);
                }
                output.write(text.getBytes()); // 写入文本
                byte[] boldEnd = bean.getBoldEnd();
                if (boldEnd!=null && boldEnd.length != 0) {
                    output.write(boldEnd);
                }
                byte[] italicEnd = bean.getItalicEnd();
                if (italicEnd!=null && italicEnd.length != 0) {
                    output.write(italicEnd);
                }
                byte[] underlineEnd = bean.getUnderlineEnd();
                if (underlineEnd!=null && underlineEnd.length != 0) {
                    output.write(underlineEnd);
                }
            } catch (IOException e) {
                e.getLocalizedMessage();
            }
            // 一条文献开始
            if (isReferenceTag(text)) {
                sb.append("\n");
            }
            sb.append(text);
        }
        Log.d("tag", sb.toString());
    }

    @Override
    public void showShortToast(String msg) {
        Toast.makeText(PoiMainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    // 判断文献开始，标志[num]
    private boolean isReferenceTag(String tag) {
        char[] content = tag.toCharArray();
        // 字符数组长度
        int len = content.length;
        int start = 0, end = 0;
        // 遍历整个字符数组，寻找'['和']'所在位置
        for (int i = 0; i < len; i++) {
            if (content[i] == '[') {
                start = i;
            }
            if (content[i] == ']') {
                end = i;
                // 已确定标记，退出循环
                break;
            }
        }
        // 截取中间字符串
        // 使用正则表达式，判断是否为数字
        if (content[start] == '[' && content[end] == ']') {
            String mid = tag.substring(start + 1, end);
            if ("".equals(mid))
                return false;
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(mid);
            if (isNum.matches()) {
                return true;
            }
        }
        return false;
    }
}
