package de.l4l.bahn3.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by l4l on 25.12.17.
 */
@Builder
@Getter
@Setter
public class Station {
    String name;
    String description;
}
