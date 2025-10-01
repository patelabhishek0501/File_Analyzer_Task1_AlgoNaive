# 📂 Luxury File Analyzer

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-007396?style=for-the-badge&logo=java&logoColor=white)
![FlatLaf](https://img.shields.io/badge/FlatLaf-4A90E2?style=for-the-badge&logo=java&logoColor=white)

**A stunning, modern file analysis tool with beautiful animations and drag-and-drop support**

[Features](#-features) • [Screenshots](#-screenshots) • [Installation](#-installation) • [Usage](#-usage) • [Tech Stack](#-tech-stack)

</div>

---

## ✨ Features

### 🎨 **Beautiful UI & Animations**
- **Animated Background** with floating particles and wave effects
- **Rotating Dashed Circle** border with smooth transitions
- **Particle Network** - particles that connect with animated lines
- **Smooth Hover Effects** on all interactive elements
- **Glass-morphism Design** with modern card layouts

### 📁 **File Analysis**
- **Drag & Drop Support** - Simply drag your file into the circle
- **Browse Files** - Click anywhere on the drop zone to browse
- **Real-time Statistics**:
  - 📝 Word Count
  - 🔠 Character Count
  - 📑 Line Count
  - 📊 File Size (in KB)
- **Animated Counter Updates** - Numbers smoothly transition

### 🌓 **Theme Support**
- Light Mode ☀️
- Dark Mode 🌙
- Smooth theme transitions

---

## 🖼️ Screenshots

### Light Mode with Animations
```
┌─────────────────────────────────────────────────────────┐
│  📂 Luxury File Analyzer              [☀ Light Theme]   │
├─────────────────────────────────────────────────────────┤
│                                                          │
│              ╭──────────────────────╮                   │
│              │      🌊 Waves        │                   │
│              │   ✨ Particles       │                   │
│              │                      │                   │
│              │    📁                │                   │
│              │  Drag and drop       │                   │
│              │  your project        │                   │
│              │  folder here.        │                   │
│              │                      │                   │
│              │  Or, browse to upload│                   │
│              ╰──────────────────────╯                   │
│                                                          │
│    📝 Words    🔠 Characters    📑 Lines    📊 Size     │
│      1,245         8,432          156      12 KB       │
└─────────────────────────────────────────────────────────┘
```

---

## 🚀 Installation

### Prerequisites
- Java 8 or higher
- FlatLaf library

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/luxury-file-analyzer.git
   cd luxury-file-analyzer
   ```

2. **Download FlatLaf Library**
   
   Download the latest FlatLaf JAR from:
   - [FlatLaf Releases](https://github.com/JFormDesigner/FlatLaf/releases)
   
   Or add via Maven:
   ```xml
   <dependency>
       <groupId>com.formdev</groupId>
       <artifactId>flatlaf</artifactId>
       <version>3.2.5</version>
   </dependency>
   ```

3. **Compile the Project**
   ```bash
   javac -cp flatlaf-3.2.5.jar LuxuryFileAnalyzer.java
   ```

4. **Run the Application**
   ```bash
   java -cp .:flatlaf-3.2.5.jar LuxuryFileAnalyzer
   ```
   
   *On Windows:*
   ```bash
   java -cp .;flatlaf-3.2.5.jar LuxuryFileAnalyzer
   ```

---

## 💻 Usage

### Drag & Drop
1. Launch the application
2. Drag any text file onto the circular drop zone
3. Watch the statistics animate in real-time!

### Browse Files
1. Click anywhere on the drop zone
2. Select a file from the file chooser dialog
3. View instant file analysis

### Toggle Theme
- Click the theme button in the top-right corner
- Switch between ☀️ Light and 🌙 Dark modes

---

## 🛠️ Tech Stack

| Technology | Purpose |
|------------|---------|
| **Java Swing** | GUI Framework |
| **FlatLaf** | Modern Look and Feel |
| **Java 2D Graphics** | Custom animations and effects |
| **Java NIO** | File reading operations |
| **Timer API** | Smooth animations |

---

## 🎯 Key Components

### 1. AnimatedBackground
- Floating particle system
- Wave animations with multiple layers
- Gradient background effects
- Particle connection lines

### 2. AnimatedDropZone
- Rotating dashed border
- Drag-and-drop functionality
- Hover effects with glow
- Custom-painted folder icon

### 3. Statistics Cards
- Real-time file analysis
- Smooth number transitions
- Hover shadow animations
- Glass-morphism design

---

## 📋 Supported File Types

Currently supports all text-based files:
- `.txt` - Text files
- `.java` - Java source files
- `.py` - Python files
- `.js` - JavaScript files
- `.html`, `.css` - Web files
- `.md` - Markdown files
- And more!

---

## 🎨 Design Philosophy

This project focuses on:
- **Modern UI/UX** - Following contemporary design trends
- **Smooth Animations** - Everything feels alive and responsive
- **User Engagement** - Interactive elements that delight users
- **Performance** - Optimized rendering and animations
- **Accessibility** - Clear contrast and readable fonts

---

## 🐛 Known Issues

- Very large files (>10MB) may take time to analyze
- Binary files are not supported
- Some special characters might not count correctly

---

## 🔮 Future Enhancements

- [ ] Support for multiple file formats (PDF, DOCX, etc.)
- [ ] Export statistics as CSV/JSON
- [ ] File comparison feature
- [ ] Batch file analysis
- [ ] Custom color themes
- [ ] Code syntax highlighting preview
- [ ] More animation effects

---

## 👨‍💻 Author

**Your Name**
- GitHub: [@abhi](https://github.com/patelabhishek0501)
- LinkedIn: [@abhi](https://linkedin.com/in/yourprofile)

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- [FlatLaf](https://www.formdev.com/flatlaf/) - For the amazing Look and Feel library
- [Java Swing](https://docs.oracle.com/javase/tutorial/uiswing/) - For the powerful GUI toolkit
- Inspiration from modern web design trends

---

## 📞 Support

If you encounter any issues or have questions:
1. Check the [Issues](https://github.com/yourusername/luxury-file-analyzer/issues) page
2. Create a new issue with detailed information
3. Star ⭐ the repository if you find it useful!

---

<div align="center">

**Made with ❤️ and Java**

If you like this project, please give it a ⭐!

</div>
