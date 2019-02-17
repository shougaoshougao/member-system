package cn.bmaster.member.enums.common;

/**
 * 性别
 * @author Wilson
 */
public enum Gender {

    MALE("男"),

    FEMALE("女");

    private String description;

    private Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
