package de.l4l.bahn3.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * Created by l4l on 25.12.17.
 */
@Builder
@Getter
public class Station {
    private String name;
    private String description;
}
