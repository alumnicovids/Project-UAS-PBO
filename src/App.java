import controller.LoginController;
import view.LoginView;
import javax.swing.SwingUtilities;

// Kelas utama (Entry Point) yang menjadi titik awal aplikasi berjalan.
public class App {

  // Method 'main' adalah fungsi yang pertama kali dipanggil oleh Java saat program di-run.
  public static void main(String[] args) {

    // SwingUtilities.invokeLater digunakan untuk memastikan kode GUI (tampilan)
    // dijalankan di thread yang aman (Event Dispatch Thread) agar aplikasi tidak macet/crash.
    SwingUtilities.invokeLater(() -> {

      // 1. Membuat objek tampilan (View) untuk halaman Login.
      LoginView loginView = new LoginView();

      // 2. Mengaktifkan logika (Controller) untuk halaman Login tersebut. Controller akan memantau tombol di loginView.
      new LoginController(loginView);

      // 3. Menampilkan jendela login ke layar pengguna (membuatnya terlihat).
      loginView.setVisible(true);
    });
  }
}
