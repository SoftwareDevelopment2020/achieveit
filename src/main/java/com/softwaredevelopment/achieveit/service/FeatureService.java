package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.softwaredevelopment.achieveit.PO.entity.Feature;
import com.softwaredevelopment.achieveit.controller.BussinessException;
import com.softwaredevelopment.achieveit.entity.FeatureVO;
import com.softwaredevelopment.achieveit.utils.POIUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author RainkQ
 * @date 2020/3/25 18:22
 */
@Service
public class FeatureService extends BaseService {


    public List<FeatureVO> getFeaturesByProjectId(String projectId) {
        Integer id = projectIdToId(projectId);
        return featuresToTree(
                iFeatureService.list(
                        new QueryWrapper<Feature>().lambda().eq(Feature::getProjectId, id)));
    }

    public boolean saveFeature(Feature feature) {
        return iFeatureService.save(feature);
    }

    public boolean saveFeatureBatch(List<Feature> features) {
        return iFeatureService.saveBatch(features);
    }


    /**
     * 解析xlsx文件并存入数据库
     *
     * @param file
     * @param projectId
     * @throws Exception
     */
    public void uploadFeature(MultipartFile file, String projectId) throws IOException, BussinessException {
        Integer id = projectIdToId(projectId);
        List<String[]> strings = POIUtil.readExcel(file);
        List<Feature> features = new ArrayList<>();
        Map<Integer, Integer> mapForFirstTier = new HashMap<>(strings.size());
        try {
            if (strings.size() == 0) {
                throw new Exception("文件读取有问题");
            }
            for (String[] ss : strings) {

                Feature nf = new Feature();
                nf.setProjectId(id);
                nf.setFirstTierId(Integer.valueOf(ss[0]));
                // 如果是这个大功能的第一条 说明要建一个secondTierId为0的额外Feature
                if (mapForFirstTier.get(nf.getFirstTierId()) == null) {
                    Feature bnf = new Feature();
                    bnf.setProjectId(id);
                    bnf.setFirstTierId(Integer.valueOf(ss[0]));
                    bnf.setSecondTierId(0);
                    bnf.setFeatureName(ss[1]);
                    bnf.setFeatureDetail(ss[2]);
                    mapForFirstTier.put(bnf.getFirstTierId(), 1);
                    features.add(bnf);
                }
                // 否则就是小功能
                nf.setSecondTierId(Integer.valueOf(ss[3]));
                nf.setFeatureName(ss[4]);
                nf.setFeatureDetail(ss[5]);
                features.add(nf);
            }
        } catch (Exception e) {
            throw new BussinessException("上传文件有问题", e.getCause());
        }
        // 存入数据库
        iFeatureService.remove(new QueryWrapper<Feature>().lambda()
                .eq(Feature::getProjectId, id));
        iFeatureService.saveBatch(features);

    }

    /**
     * 把Feature变成树形
     *
     * @param features
     * @return
     */
    public List<FeatureVO> featuresToTree(List<Feature> features) {
        Map<Integer, FeatureVO> map = new HashMap<>(features.size());
        for (Feature f : features) {
            // 高层功能
            if (f.getSecondTierId() == 0) {
                FeatureVO fvo = new FeatureVO();
                fvo.setLabel(f.getFeatureName());
                fvo.setDetail(f.getFeatureDetail());
                fvo.setChildren(new ArrayList<>());
                map.put(f.getFirstTierId(), fvo);
            }
        }
        for (Feature f : features) {
            // 低层功能
            if (f.getSecondTierId() != 0) {
                FeatureVO fvo = new FeatureVO();
                fvo.setLabel(f.getFeatureName());
                fvo.setDetail(f.getFeatureDetail());
                fvo.setChildren(null);
                // 如果出现无父功能的子功能 当作父功能添加入map
                if (map.get(f.getFirstTierId()) == null) {
                    map.put(f.getFirstTierId(), fvo);
                } else {
                    // 否则加入父功能的children
                    map.get(f.getFirstTierId()).getChildren().add(fvo);
                }
            }
        }
        return new ArrayList<>(map.values());
    }
}
