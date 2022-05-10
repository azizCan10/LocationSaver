package com.example.mapsdeneme.kullaniciIslemleri

class Kullanici {
    var id: Int =0;
    var kullaniciAdi: String ="";
    var sifre: String ="";
    constructor(kullaniciAdi: String,sifre: String){
        this.kullaniciAdi=kullaniciAdi
        this.sifre=sifre
    }
    constructor(){}
}