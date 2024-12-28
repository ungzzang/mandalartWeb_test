package com.green1st.mandalartWeb.mandalart.model;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Getter
@Component
public class ColorCodes {
    // 0레벨 색상
    private  List<String> titleColor = Arrays.asList(
            "#001A00", "#003300", "#004C00", "#006600", "#007F00", "#339933", "#66CC66", "#99FF99"
    );
    // 1레벨 색상
    private  List<String> subTitleColor = Arrays.asList(
            "#99FF99FF", "#99FF99E6", "#99FF99CC", "#99FF99B3", "#99FF9999", "#99FF997F", "#99FF9966", "#99FF994C"
    );

    private  List<String> defaultColor = Arrays.asList("#FFFFFF");  // 리스트로 변경

    public List<String> getTitleColor() {
        return titleColor;
    }

    public List<String> getSubTitleColor() {
        return subTitleColor;
    }

    public List<String> getDefaultColor() {
        return defaultColor;
    }

}
