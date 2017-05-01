package graduation.dsctimetable.list;

/**
 * Created by leejs on 2016-10-12.
 */
public class MemoListItem {
    private String[] mData;

    public MemoListItem(String[] data) {
        mData = data;
    }

//    public MemoListItem(int memo_no, String memo, String m_date, String stu_no, String sub_name, int tt_index) {
    public MemoListItem(int memo_no, String memo, String m_date) {
        mData = new String[3];
        mData[0] = Integer.toString(memo_no);
        mData[1] = memo;
        mData[2] = m_date;
//        mData[3] = stu_no;
//        mData[4] = sub_name;
//        mData[5] = Integer.toString(tt_index);

    }

    public String[] getData() {
        return mData;
    }

    public String getData(int index) {
        return mData[index];
    }

    public void setData(String[] data) {
        mData = data;
    }
}
