package cn.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class IndexData implements Serializable {
   private String date;
   private float closePoint;
}
