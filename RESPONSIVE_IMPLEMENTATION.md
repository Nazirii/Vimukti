# Responsive Gaming Implementation

## Perubahan yang Dilakukan

Implementasi ini membuat game menjadi responsive dan tidak stretch ketika fullscreen atau di-resize.

### 1. GameConstants.java
- Mendefisikan resolusi target game: 1280x720
- Menentukan ukuran minimum viewport: 800x600

### 2. MainGame.java
- Menambahkan OrthographicCamera untuk kontrol tampilan
- Menggunakan FitViewport untuk scaling responsive
- Implementasi resize() method untuk mengupdate viewport

### 3. Screen Classes (GameScreen, MenuScreen, PauseScreen, GameOverScreen)
- Menggunakan viewport dari MainGame untuk konsistensi
- Background dan UI elements menggunakan konstanta ukuran alih-alih Gdx.graphics size
- Implementasi resize() method di setiap screen

### 4. Lwjgl3Launcher.java
- Mengubah ukuran window default menjadi 1280x720
- Mengaktifkan window resizing
- Menentukan ukuran minimum window: 800x600

## Cara Kerja

1. **FitViewport**: Menjaga aspect ratio dengan menambahkan letterbox/pillarbox jika diperlukan
2. **Fixed Game Coordinates**: Game menggunakan koordinat virtual 1280x720 
3. **Responsive Scaling**: Viewport otomatis scale sesuai ukuran window sambil menjaga proportions
4. **No Stretching**: Element UI dan gameplay tetap proportional di berbagai ukuran layar

## Keuntungan

- Game tidak stretch di fullscreen
- Aspect ratio tetap terjaga
- UI elements tetap proporsional
- Kompatibel dengan berbagai resolusi layar
- Window bisa di-resize tanpa merusak tampilan
