package com.example.capstoneproject.XApi.Response;

import com.example.capstoneproject.XApi.DTOs.XUserDTO;

import java.util.List;

public class XFollowersResponse {
    private List<XUserDTO> data;
    private Meta meta;

    public List<XUserDTO> getData() { return data; }
    public void setData(List<XUserDTO> data) { this.data = data; }

    public Meta getMeta() { return meta; }
    public void setMeta(Meta meta) { this.meta = meta; }

    public static class Meta {
        private String next_token;
        public String getNext_token() { return next_token; }
        public void setNext_token(String next_token) { this.next_token = next_token; }
    }
}
