package org.dimasik.lootmanager.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.dimasik.lootmanager.frontend.menus.Configs;

@Data
@AllArgsConstructor
public class NameData {
    private Configs back;
    private String name;
    private Boolean forceClose;
}
