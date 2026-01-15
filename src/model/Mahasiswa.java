package model;

import java.sql.Date;

// Kelas ini merepresentasikan struktur data satu mahasiswa.
public class Mahasiswa {

  // Variabel-variabel ini menyimpan data pribadi mahasiswa (sesuai kolom di database).
  // Dibuat 'private' agar tidak bisa diubah sembarangan dari luar (Encapsulation).
  private String nim;
  private String nama;
  private Date tanggalLahir;
  private String prodi;

  // Konstruktor: Fungsi yang dipanggil saat kita membuat objek mahasiswa baru (new Mahasiswa(...)).
  // Bertugas mengisi variabel-variabel di atas dengan data yang dikirim.
  public Mahasiswa(String nim, String nama, Date tanggalLahir, String prodi) {
    this.nim = nim;
    this.nama = nama;
    this.tanggalLahir = tanggalLahir;
    this.prodi = prodi;
  }

  // Getter Methods:
  // Karena variabelnya 'private', kita butuh fungsi-fungsi ini untuk mengambil (membaca) datanya dari luar.
  // Contoh: m.getNama() akan memberikan nama mahasiswa tersebut.
  public String getNim() { return nim; }
  public String getNama() { return nama; }
  public Date getTanggalLahir() { return tanggalLahir; }
  public String getProdi() { return prodi; }
}
