# 📦 Respy Package Manager

Bu proje, **Spring Boot**, **PostgreSQL**, **MinIO** ve **Docker** kullanılarak hazırlanmış bir paket yönetim sistemidir.  
Proje kapsamında dosya yükleyebilir (deployment) ve yüklenen dosyaları indirebilirsiniz (download).

---

## 🚀 Kullanılan Teknolojiler
- Java 17
- Spring Boot 3
- PostgreSQL 14
- MinIO (S3 Compatible Object Storage)
- Docker & Docker Compose
- Hibernate ORM
- Maven

---

## 🛠️ Kurulum Adımları

1. **Proje klasörüne girin**:
   ```bash
   cd respy-package
   ```

2. **Docker ortamını başlatın**:
   ```bash
   docker compose up --build
   ```
   Bu adım şunları kurar:
    - PostgreSQL veritabanı (`localhost:5432`)
    - Respy Package Manager uygulaması (`localhost:8080`)
    - MinIO sunucusu (`localhost:9000`) ve yönetim konsolu (`localhost:9001`)

3. **MinIO Web UI'ya Giriş Yapın**:
    - Tarayıcıdan açın: [http://localhost:9001](http://localhost:9001)
    - Kullanıcı Adı: `respyadmin`
    - Şifre: `respyproject`

4. **PostgreSQL Bağlantı Bilgileri**:
    - Host: `localhost`
    - Port: `5432`
    - Veritabanı Adı: `postgres`
    - Kullanıcı Adı: `postgres`
    - Şifre: `1`

---

## 📑 API Kullanımı

### 📤 Paket Yükleme (Deployment)

- **Endpoint**:  
  `POST /api/package/{packageName}/{version}`

- **Açıklama**:  
  Belirtilen isim ve versiyonla bir paket (rep + meta) dosyası yükler.

- **Örnek cURL Komutu**:
  ```bash
  curl -X POST http://localhost:8080/api/package/mypackage/1.0.0 \
    -F "packageFile=@/path/to/package.rep" \
    -F "metaFile=@/path/to/meta.json"
  ```

---

### 📥 Paket İndirme (Download)

- **Endpoint**:  
  `GET /api/package/{packageName}/{version}/{fileName}`

- **Açıklama**:  
  Belirtilen paketin dosyasını indirir.

- **Örnek**:
  ```
  GET http://localhost:8080/api/package/mypackage/1.0.0/sample.rep
  ```

---

## 📂 Proje Yapısı

```
respy-package/
├── docker-compose.yml
├── Dockerfile
├── README.md
├── respy-package-manager/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/respy/
│   │   │   │   ├── controller/
│   │   │   │   ├── model/
│   │   │   │   ├── service/
│   │   │   │   ├── storage/
│   │   │   ├── resources/
│   ├── pom.xml
```

---

## 📝 Notlar
- İlk çalıştırmada `packages` adında bir MinIO bucket'ı otomatik oluşturulur.
- Yüklenen dosyalar hem MinIO'ya hem PostgreSQL veritabanına kaydedilir.
- Proje hem **file-system-storage** hem **object-storage** stratejisine uygun geliştirilmiştir.  
  (Şu anda **object-storage** yani MinIO kullanılıyor.)

---

# ✅ Başarıyla kurulum ve test tamamlandı!
