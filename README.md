# Polmon - Virtual Pet Game

Polmon adalah game virtual pet yang terinspirasi dari Tamagotchi, di mana pemain dapat merawat monster virtual mereka sendiri. Game ini dikembangkan menggunakan Java dengan antarmuka grafis Swing.

## Deskripsi

Polmon menghadirkan pengalaman merawat monster virtual yang interaktif dan menyenangkan. Pemain bertanggung jawab untuk memberi makan, bermain, dan merawat monster mereka agar tetap sehat dan bahagia. Monster akan berevolusi dari telur hingga dewasa seiring berjalannya waktu.

## Fitur Utama

### 1. Sistem Monster
- **Dua Spesies**: Kucingmon dan Slimemon
- **Statistik Lengkap**: HP, Energy, Happiness, Hunger
- **Sistem State**: Monster memiliki berbagai kondisi (Normal, Hungry, Bored, Sleep, Dead)
- **Timestamp Tracking**: Melacak kapan terakhir monster diberi makan dan dirawat

### 2. Sistem Evolusi
Monster akan berevolusi melalui 4 tahap kehidupan:
- **EGG** (Telur) - Tahap awal
- **BABY** (Bayi) - Setelah menetas
- **CHILD** (Anak) - Tahap pertumbuhan
- **ADULT** (Dewasa) - Tahap final

Evolusi terjadi secara otomatis berdasarkan umur monster dan kondisinya.

### 3. Interaksi dengan Monster
- **Feed (Beri Makan)**: Mengurangi hunger dan meningkatkan HP
- **Play (Bermain)**: Meningkatkan happiness dan mengurangi energy
- **Sleep (Tidur)**: Memulihkan energy monster
- **Wake Up (Bangunkan)**: Membangunkan monster dari tidur

### 4. State Management
Monster memiliki berbagai state yang mempengaruhi perilakunya:
- **NormalState**: Kondisi normal, semua aksi tersedia
- **HungryState**: Monster lapar (hunger >= 80), happiness menurun
- **BoredState**: Monster bosan (happiness <= 20), HP menurun
- **SleepState**: Monster tidur, memulihkan energy
- **DeadState**: Monster mati (HP <= 0), tidak ada aksi yang bisa dilakukan

### 5. Save & Load System
- Sistem save otomatis saat aplikasi ditutup
- Data disimpan dalam format XML di folder `saves/`
- Monster akan kembali dengan semua statistik dan kondisi yang tersimpan

### 6. Event System
Game dilengkapi dengan event system yang memberikan notifikasi untuk:
- Perubahan evolusi
- Perubahan state
- Perubahan HP

### 7. Visual Features
- Sprite dinamis untuk setiap spesies dan tahap evolusi
- Animasi perubahan state
- UI yang menampilkan statistik real-time
- Progress bar untuk HP, Energy, dan Happiness

## Struktur Project

```
Polmon-izin-serius/
├── src/
│   ├── core/
│   │   ├── Main.java              # Entry point aplikasi
│   │   ├── GameEngine.java        # Engine utama game (Singleton)
│   │   └── ShutdownHandler.java   # Handler untuk save saat exit
│   ├── model/
│   │   ├── Monster.java           # Model utama monster
│   │   ├── MonsterDatabase.java   # Database nama dan sprite monster
│   │   ├── EvolutionManager.java  # Manager sistem evolusi
│   │   ├── EvolutionStage.java    # Enum tahap evolusi
│   │   ├── SpeciesType.java       # Enum jenis spesies
│   │   └── state/
│   │       ├── PolmonState.java   # Interface state pattern
│   │       ├── NormalState.java   # State normal
│   │       ├── HungryState.java   # State lapar
│   │       ├── BoredState.java    # State bosan
│   │       ├── SleepState.java    # State tidur
│   │       └── DeadState.java     # State mati
│   ├── utils/
│   │   ├── MonsterReader.java     # Utility untuk load data
│   │   ├── MonsterWriter.java     # Utility untuk save data
│   │   ├── TimeSimulator.java     # Simulator waktu
│   │   ├── ResourceHelper.java    # Helper untuk resources
│   │   └── event/
│   │       ├── EventManager.java  # Manager event system
│   │       ├── EventListener.java # Interface listener
│   │       └── GameEvent.java     # Model event
│   ├── view/
│   │   ├── GameWindow.java        # Window utama game
│   │   ├── GameCanvas.java        # Canvas untuk rendering
│   │   ├── Renderer.java          # Renderer grafis
│   │   ├── ActionHandler.java     # Handler untuk aksi pemain
│   │   ├── StatsDialog.java       # Dialog untuk statistik
│   │   ├── AssetLoader.java       # Loader untuk asset
│   │   ├── SpriteGenerator.java   # Generator sprite
│   │   ├── adapter/
│   │   │   ├── ImageAdapter.java       # Interface adapter pattern
│   │   │   ├── PNGAdapter.java         # Adapter untuk PNG
│   │   │   └── PlaceHolderAdapter.java # Adapter placeholder
│   │   └── components/
│   │       ├── StatBar.java       # Component bar statistik
│   │       └── StateLabel.java    # Component label state
│   └── resources/
│       └── images/                # Folder untuk gambar
└── test/
    ├── model/
    │   ├── MonsterTest.java           # Test untuk Monster
    │   ├── MonsterDatabaseTest.java   # Test untuk database
    │   ├── EvolutionManagerTest.java  # Test untuk evolusi
    │   └── state/
    │       └── StateTransitionTest.java # Test transisi state
    └── utils/
        ├── SaveLoadTest.java          # Test save/load
        └── event/
            └── EventSystemTest.java   # Test event system
```

## Design Patterns yang Digunakan

### 1. Singleton Pattern
- **GameEngine**: Memastikan hanya ada satu instance game engine

### 2. State Pattern
- **PolmonState**: Mengatur behavior monster berdasarkan kondisinya
- Implementasi: NormalState, HungryState, BoredState, SleepState, DeadState

### 3. Observer Pattern (Event System)
- **EventManager**: Mengelola event dan notifikasi
- **EventListener**: Interface untuk listener
- Digunakan untuk: Evolution events, State change events, Health change events

### 4. Adapter Pattern
- **ImageAdapter**: Interface untuk adapter gambar
- **PNGAdapter**: Adapter untuk format PNG
- **PlaceHolderAdapter**: Adapter untuk placeholder image

## Teknologi yang Digunakan

- **Java**: Bahasa pemrograman utama
- **Swing**: Library untuk GUI
- **JUnit**: Framework untuk unit testing
- **XML**: Format untuk save/load data
- **Maven/Gradle**: Build tool (opsional)

## Cara Menjalankan

### Prasyarat
- Java Development Kit (JDK) 8 atau lebih baru
- Java Runtime Environment (JRE)

### Kompilasi
```bash
# Compile semua file Java
javac -d bin src/**/*.java

# Atau jika menggunakan struktur package
javac -d bin -sourcepath src src/core/Main.java
```

### Menjalankan Game
```bash
# Jalankan dari direktori root project
java -cp bin core.Main
```

### Menjalankan Unit Tests
```bash
# Compile tests
javac -d bin -cp bin:junit.jar test/**/*.java

# Jalankan tests
java -cp bin:junit.jar org.junit.runner.JUnitCore test.model.MonsterTest
```

## Cara Bermain

### Memulai Game
1. Jalankan aplikasi
2. Jika pertama kali bermain, game akan membuat monster baru secara random
3. Jika sudah pernah bermain, monster akan dimuat dari save file

### Merawat Monster
1. **Memberi Makan**: Klik tombol "Feed" untuk mengurangi hunger
   - Mengurangi hunger sebesar 30
   - Menambah HP sebesar 5
   - Mengupdate timestamp terakhir diberi makan

2. **Bermain**: Klik tombol "Play" untuk meningkatkan kebahagiaan
   - Menambah happiness sebesar 20
   - Mengurangi energy sebesar 15
   - Mengupdate timestamp terakhir dirawat

3. **Tidur**: Klik tombol "Sleep" untuk istirahat
   - Memulihkan energy secara bertahap
   - Monster tidak bisa melakukan aksi lain saat tidur

4. **Bangunkan**: Klik tombol "Wake Up" untuk membangunkan monster
   - Hanya bisa dilakukan saat monster sedang tidur

### Memantau Kondisi Monster
- **HP Bar**: Indikator kesehatan monster (hijau)
- **Energy Bar**: Indikator energi monster (biru)
- **Happiness Bar**: Indikator kebahagiaan monster (kuning)
- **State Label**: Menampilkan kondisi monster saat ini
- **Stats Dialog**: Klik "Stats" untuk melihat statistik lengkap

### Tips Bermain
1. Beri makan monster secara teratur agar hunger tidak mencapai 80+
2. Ajak bermain monster agar happiness tetap di atas 20
3. Pastikan monster istirahat saat energy rendah
4. Perhatikan perubahan state untuk tahu kondisi monster
5. HP akan berkurang setiap jam, pastikan selalu memberi makan

## Mekanisme Game

### Sistem Waktu
- **Logic Timer**: Update setiap 1 detik untuk game logic
- **Visual Timer**: Update setiap 0.5 detik untuk visual
- **Hourly Timer**: Monster akan starve (HP -1) setiap 1 jam real-time

### Kondisi Evolusi
Monster akan berevolusi berdasarkan umur (dalam detik):
- EGG → BABY: Setelah beberapa detik/menit
- BABY → CHILD: Setelah mencapai umur tertentu
- CHILD → ADULT: Setelah mencapai umur dewasa

### Kondisi State Transition
- **Normal → Hungry**: Ketika hunger >= 80
- **Normal → Bored**: Ketika happiness <= 20
- **Any → Dead**: Ketika HP <= 0
- **Any → Normal**: Ketika hunger < 50 dan happiness > 30
- **Any → Sleep**: Ketika pemain klik Sleep

### Mekanisme Death
Jika monster mati (HP mencapai 0):
- Monster masuk ke DeadState
- Semua aksi menjadi tidak tersedia
- Pemain harus memulai game baru

## Save File

Data monster disimpan di:
```
./saves/polmon.xml
```

Format XML menyimpan:
- ID dan nama monster
- Statistik (HP, Energy, Happiness, Hunger)
- Timestamp terakhir diberi makan dan dirawat
- Umur monster
- Tahap evolusi
- Spesies

## Testing

Project ini dilengkapi dengan comprehensive unit tests:

### Model Tests
- **MonsterTest**: Test untuk logika monster dasar
- **MonsterDatabaseTest**: Test untuk database monster
- **EvolutionManagerTest**: Test untuk sistem evolusi
- **StateTransitionTest**: Test untuk transisi antar state

### Utils Tests
- **SaveLoadTest**: Test untuk sistem save/load
- **EventSystemTest**: Test untuk event system

### Menjalankan All Tests
```bash
# Menggunakan IDE: Run all tests in test/ directory
# Menggunakan command line dengan JUnit
java -cp bin:junit.jar org.junit.runner.JUnitCore test.AllTests
```

## Pengembangan Lebih Lanjut

### Fitur yang Dapat Ditambahkan
- [ ] Lebih banyak spesies monster
- [ ] Mini games untuk bermain dengan monster
- [ ] Item dan inventory system
- [ ] Multiple monsters (koleksi)
- [ ] Achievement system
- [ ] Sound effects dan background music
- [ ] Customization untuk monster
- [ ] Online leaderboard
- [ ] Battle system antar monster

### Improvements
- [ ] UI/UX yang lebih modern
- [ ] Animasi yang lebih smooth
- [ ] Configuration file untuk game settings
- [ ] Localization (multi-language)
- [ ] Performance optimization

## Kontributor

Project ini dikembangkan sebagai tugas kuliah/project pembelajaran.

## Lisensi

Project ini dibuat untuk tujuan edukasi.

## Troubleshooting

### Game tidak bisa menyimpan
- Pastikan folder `saves/` dapat ditulis
- Check permissions pada direktori project

### Monster langsung mati
- Check save file, mungkin HP sudah 0
- Mulai game baru dengan menghapus save file

### Error saat compile
- Pastikan menggunakan JDK 8 atau lebih baru
- Check classpath sudah benar
- Pastikan semua dependencies tersedia

### UI tidak muncul
- Pastikan Java Swing tersedia
- Check display settings
- Coba jalankan dengan administrator/sudo jika perlu

## Kontak & Support

Untuk pertanyaan atau bug report, silakan buat issue di repository ini.

---

**Selamat bermain dan rawat monster Anda dengan baik!** 🎮
