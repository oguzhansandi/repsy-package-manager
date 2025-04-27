# 📦 Respy Package Manager

Bu proje, **Spring Boot**, **PostgreSQL**, **MinIO**, **Docker** ve **Maven** kullanılarak hazırlanmış bir **paket yönetim sistemidir**.  
Proje kapsamında ayrıca **Repsy.io** üzerinden özel bir Maven Repository kullanılmış ve storage modülleri deploy edilmiştir.

Bu sistem ile:
- Dosya yükleyebilir (**deployment**)
- Yüklenen dosyaları indirebilirsiniz (**download**).

---

## 🚀 Kullanılan Teknolojiler
- Java 17 (LTS)
- Spring Boot 3
- PostgreSQL 14
- MinIO (S3 Compatible Object Storage)
- Docker & Docker Compose
- Hibernate ORM
- Maven
- Repsy (Private Maven Repository)

---

## 🛠️ Kurulum Adımları

1. **Proje klasörünüze girin**:
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

5. **Storage Stratejisini Belirleyin**:
   `application.properties` dosyasından veya ortam değişkeni ile seçebilirsiniz:
   ```properties
   storage.strategy=object-storage  # veya file-system
   ```

---

## 💑 API Kullanımı

### 📤 Paket Yükleme (Deployment)

- **Endpoint**:  
  `POST /api/package/{packageName}/{version}`

- **Açıklama**:  
  Belirtilen isim ve versiyonla bir paket (`package.rep` ve `meta.json`) yükler.

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
  ```bash
  curl -O http://localhost:8080/api/package/mypackage/1.0.0/sample.rep
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
├── file-system-storage/   # Storage Strategy 1
├── object-storage/        # Storage Strategy 2
├── storage-api/            # Ortak Interface'ler
```

---

## 🏦 Storage Yapısı

- **File System Storage**: Paketler yerel dosya sistemine kaydedilir.
- **Object Storage**: Paketler MinIO nesne depolamaya kaydedilir.
- **Storage API**: Ortak interface yapılarını barındırır.

Storage stratejisi çalışma zamanında `application.properties` ya da ortam değişkeniyle belirlenebilir.

---

## 🔄 Repsy Kullanımı

Projede 3 adet modül özel Maven repository olan **Repsy.io**'ya deploy edilmiştir:

| Modul              | Artifact Adı          | Versiyon       |
|:-------------------|:----------------------|:--------------|
| storage-api         | `storage-api`          | 0.0.1-SNAPSHOT |
| file-system-storage | `file-system-storage`  | 0.0.1-SNAPSHOT |
| object-storage      | `object-storage`       | 0.0.1-SNAPSHOT |

### Maven Deploy Yapılandırması

`.m2/settings.xml` dosyasına Repsy bilgileriniz eklenmelidir:

```xml
<settings>
  <servers>
    <server>
      <id>repsy</id>
      <username>oguzhansandi</username>
      <password>PASSWORD</password>
    </server>
  </servers>
</settings>
```

**pom.xml** içinde `distributionManagement` tanımlaması:

```xml
<distributionManagement>
  <repository>
    <id>repsy</id>
    <name>My Private Maven Repository on Repsy</name>
    <url>https://repo.repsy.io/mvn/oguzhansandi/repsy-storage-module/</url>
  </repository>
</distributionManagement>
```

Deploy etmek için:

```bash
mvn clean deploy
```

### Maven Download Repository Ayarı

Projede bu modülleri çekebilmek için repositories ayarı:

```xml
<repositories>
  <repository>
    <id>repsy</id>
    <name>My Private Maven Repository on Repsy</name>
    <url>https://repo.repsy.io/mvn/oguzhansandi/repsy-storage-module/</url>
  </repository>
</repositories>
```

---

## 📝 Notlar
- İlk çalıştırmada `packages` adında bir MinIO bucket'ı otomatik oluşturulur.
- Yüklenen dosyalar hem MinIO'ya hem PostgreSQL veritabanına kaydedilir.
- Proje hem **file-system-storage** hem **object-storage** stratejisine uygundur.
- API guideline, HTTP metotları ve response kodları en iyi uygulamalara uygundur.
- Uygulama Docker imajı olarak paketlenmiş ve Repsy Public Docker Repository'ye yüklenmiştir.

---

# ✅ Başarıyla kurulum ve test tamamlandı!
