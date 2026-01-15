# Project UAS - Dashboard Manajemen Mahasiswa

Aplikasi desktop berbasis Java (Swing) untuk manajemen data mahasiswa menggunakan arsitektur MVC (Model-View-Controller) dan database MySQL.

## 📌 Fitur Utama

- **Login Admin**: Keamanan akses menggunakan username dan password database.
- **Dashboard Statistik**: Visualisasi jumlah total mahasiswa dan prodi terpopuler.
- **CRUD Data**: Input, Edit, Hapus, dan Lihat data mahasiswa.
- **Pencarian Real-time**: Mencari data mahasiswa tanpa reload.
- **Validasi Input**: Mencegah data kosong atau duplikasi NIM.

---

## 🗺️ Alur Program (Flowchart)

Berikut adalah diagram alur kerja sistem, mulai dari proses login hingga manajemen data. Diagram ini digenerate secara otomatis menggunakan **Mermaid JS**.

### 1. Alur Login (Authentication Flow)

Diagram ini menjelaskan proses dari saat aplikasi dijalankan hingga admin berhasil masuk ke dashboard.

```mermaid
flowchart TD
    %% Styling
    classDef file fill:#f9f9f9,stroke:#333,stroke-width:2px;
    classDef logic fill:#d1e7dd,stroke:#0f5132,stroke-width:1px;
    classDef view fill:#cfe2ff,stroke:#084298,stroke-width:1px;
    classDef db fill:#fff3cd,stroke:#664d03,stroke-width:1px;

    subgraph App_Java [File: App.java]
        Start([Mulai Program]) --> Main[main()]
    end

    subgraph View_Login [File: view/LoginView.java]
        InitLogin[new LoginView()]
        ShowLogin[setVisible/Tampil]
    end

    subgraph Controller_Login [File: controller/LoginController.java]
        InitCont[new LoginController()] --> BtnClick{Tombol Login Diklik?}
        BtnClick -- Ya --> GetInput[Ambil User & Pass]
        GetInput --> CallCheck
        CheckResult{Login Sukses?}
    end

    subgraph Model_User [File: model/UserDAO.java]
        CallCheck[login()] --> Query[SELECT * FROM users...]
    end

    subgraph View_Main [File: view/MainView.java]
        InitMain[new MainView()]
    end

    subgraph Controller_Mhs [File: controller/MahasiswaController.java]
        InitMhsCont[new MahasiswaController()]
    end

    %% Connections
    Main --> InitLogin
    InitLogin --> InitCont
    InitCont --> ShowLogin

    CheckResult -- Tidak --> ShowMsg[Muncul Pesan Error]
    CheckResult -- Ya --> Dispose[Tutup LoginView]
    Dispose --> InitMain
    InitMain --> InitMhsCont
    InitMhsCont --> Finish([Masuk Dashboard])

    %% Assign Classes
    Main:::file
    InitLogin:::view
    ShowLogin:::view
    InitCont:::logic
    BtnClick:::logic
    GetInput:::logic
    CheckResult:::logic
    CallCheck:::db
    Query:::db
    InitMain:::view
    InitMhsCont:::logic
```

### 2. Alur Manajemen Data (CRUD Operations)

Diagram ini menjelaskan bagaimana Controller mengatur logika saat tombol Simpan, Edit, Hapus, atau Cari ditekan.

```mermaid
flowchart TD
    %% Styling
    classDef logic fill:#d1e7dd,stroke:#0f5132,stroke-width:1px;
    classDef db fill:#fff3cd,stroke:#664d03,stroke-width:1px;
    classDef view fill:#cfe2ff,stroke:#084298,stroke-width:1px;

    subgraph User_Action [Aksi Pengguna di MainView]
        ClickSave[Klik 'SIMPAN']
        ClickDelete[Klik 'HAPUS']
        ClickEdit[Klik 'EDIT']
        ClickSearch[Ketik di Kolom Cari]
    end

    subgraph Controller [File: controller/MahasiswaController.java]
        FuncSave[saveMahasiswa()]
        FuncDel[deleteMahasiswa()]
        FuncEdit[editMahasiswa()]
        FuncSearch[searchData()]
        FuncRefresh[refreshTable()]

        Validasi{Data Lengkap?}
        Confirm{Konfirmasi?}
    end

    subgraph DAO [File: model/MahasiswaDAO.java]
        DBInsert[insert()]
        DBDelete[delete()]
        DBUpdate[update()]
        DBSearch[search()]
        DBGetAll[getAll()]
    end

    %% Flow Simpan
    ClickSave --> FuncSave
    FuncSave --> Validasi
    Validasi -- Tidak --> MsgError[Pesan Peringatan]
    Validasi -- Ya --> DBInsert
    DBInsert --> FuncRefresh

    %% Flow Hapus
    ClickDelete --> FuncDel
    FuncDel --> Confirm
    Confirm -- Ya --> DBDelete
    DBDelete --> FuncRefresh

    %% Flow Edit
    ClickEdit --> FuncEdit
    FuncEdit --> DBUpdate
    DBUpdate --> FuncRefresh

    %% Flow Cari
    ClickSearch --> FuncSearch
    FuncSearch --> DBSearch
    DBSearch --> UpdateTable[Update Tampilan Tabel]

    %% Flow Refresh
    FuncRefresh --> DBGetAll
    DBGetAll --> UpdateTable

    %% Assign Classes
    ClickSave:::view
    ClickDelete:::view
    ClickEdit:::view
    ClickSearch:::view
    FuncSave:::logic
    FuncDel:::logic
    FuncEdit:::logic
    FuncSearch:::logic
    FuncRefresh:::logic
    DBInsert:::db
    DBDelete:::db
    DBUpdate:::db
    DBSearch:::db
    DBGetAll:::db
```
