package ncm.util;

import ncm.model.NCMAllModel;
import ncm.model.NCMSongModel;
import ncm.model.NCMWeekModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenxiangzhou214164 on 2017/8/2.
 */
public class NCMUtil {

    public static void setWeekCalls(List<NCMWeekModel> ncmWeekModelList) {
        if (ncmWeekModelList == null || ncmWeekModelList.size() == 0) {
            return;
        }
        double minWidth = 100.0;
        int size =ncmWeekModelList.size();
        for (int i = 0; i < size - 1; i++) {
            double gap = ncmWeekModelList.get(i).getWidth() - ncmWeekModelList.get(i + 1).getWidth();
            if (gap > Double.MIN_VALUE && gap < minWidth) {
                minWidth = gap;
            }
        }
        minWidth = minWidth < ncmWeekModelList.get(size - 1).getWidth() ? minWidth : ncmWeekModelList.get(size - 1).getWidth();
        for (NCMWeekModel ncmWeekModel:
                ncmWeekModelList) {
            ncmWeekModel.setListen((int) Math.round(ncmWeekModel.getWidth() / minWidth));
        }
    }

    public static void setAllCalls(List<NCMAllModel> ncmAllModelList) {
        if (ncmAllModelList == null || ncmAllModelList.size() == 0) {
            return;
        }
        double minWidth = 100.0;
        int size =ncmAllModelList.size();
        for (int i = 0; i < size - 1; i++) {
            double gap = ncmAllModelList.get(i).getWidth() - ncmAllModelList.get(i + 1).getWidth();
            if (gap > Double.MIN_VALUE && gap < minWidth) {
                minWidth = gap;
            }
        }
        minWidth = minWidth < ncmAllModelList.get(size - 1).getWidth() ? minWidth : ncmAllModelList.get(size - 1).getWidth();
        for (NCMAllModel ncmAllModel:
                ncmAllModelList) {
            ncmAllModel.setListen((int) Math.round(ncmAllModel.getWidth() / minWidth));
        }
    }

    public static NCMSongModel convertWeekToSong(NCMWeekModel ncmWeekModel) {
        NCMSongModel ncmSongModel = new NCMSongModel();
        ncmSongModel.setName(ncmWeekModel.getSong());
        ncmSongModel.setSongId(ncmWeekModel.getSongId());
        ncmSongModel.setSinger(ncmWeekModel.getSinger());
        ncmSongModel.setSingerId(ncmWeekModel.getSingerId());
        ncmSongModel.setListenDate(ncmWeekModel.getDate());
        return ncmSongModel;
    }

    public static List<NCMSongModel> convertWeekToSong(List<NCMWeekModel> ncmWeekModelList) {
        List<NCMSongModel> ncmSongModelList = new ArrayList<NCMSongModel>();
        for (NCMWeekModel ncmWeekModel:
                ncmWeekModelList) {
            ncmSongModelList.add(convertWeekToSong(ncmWeekModel));
        }
        return ncmSongModelList;
    }
}
