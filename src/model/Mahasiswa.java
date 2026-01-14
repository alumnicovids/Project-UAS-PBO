package model;

import java.sql.Date;

public class Mahasiswa {
    private String nim;
    private String nama;
    private Date tanggalLahir;
    private String prodi;

    public Mahasiswa(String nim, String nama, Date tanggalLahir, String prodi) {
        this.nim = nim;
        this.nama = nama;
        this.tanggalLahir = tanggalLahir;
        this.prodi = prodi;
    }

    public String getNim() { return nim; }
    public String getNama() { return nama; }
    public Date getTanggalLahir() { return tanggalLahir; }
    public String getProdi() { return prodi; }
}
