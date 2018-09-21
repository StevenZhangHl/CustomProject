package com.horen.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author:Steven
 * Time:2018/8/13 15:12
 * Description:This isTypeBean
 */
public class TypeBean implements Parcelable {
    private String typeId;
    private String typeName;
    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public TypeBean(String typeId, String typeName,boolean isChecked) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.isChecked = isChecked;
    }

    protected TypeBean(Parcel in) {
        typeId = in.readString();
        typeName = in.readString();
    }

    public static final Creator<TypeBean> CREATOR = new Creator<TypeBean>() {
        @Override
        public TypeBean createFromParcel(Parcel in) {
            return new TypeBean(in);
        }

        @Override
        public TypeBean[] newArray(int size) {
            return new TypeBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(typeId);
        parcel.writeString(typeName);
    }
}
