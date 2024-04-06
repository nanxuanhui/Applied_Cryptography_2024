<template>
  <v-app>
    <v-container>
      <v-row>
        <v-col cols="12">
          <v-card>
            <v-card-title>File Download Selection</v-card-title>
            <div v-if="userstatus === '1'">
              <h3>upload your file to Decrypt</h3>
    <input type="file"  @change="handleEncryptedFileUpload" >
  </div>
            <v-card-text>
              <!-- <v-btn color="success" @click="dialog = true">Upload File</v-btn> -->
              <v-btn color="success" @click="dialog = true" v-if="userRole === '1'">Upload File</v-btn>

              <v-data-table :headers="headers" :items="files" :items-per-page="5" class="elevation-1">
                <template v-slot:item.actions="{ item }">
                  <!-- <v-btn color="primary" @click="() => download(item)">Encrypt & Download</v-btn> -->
                  <v-btn color="primary" @click="() => download(item)">Download</v-btn>
                  <!-- <v-btn color="secondary" @click="() => download(item)">Decrypt & Download</v-btn> -->
                </template>
              </v-data-table>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
    <v-dialog v-model="dialog" persistent max-width="600px">
      <v-card>
        <v-card-title>Upload a file</v-card-title>
        <v-card-text>
          <input type="file" @change="handleFileUpload">
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false">Cancel</v-btn>
          <v-btn color="blue darken-1" text @click="uploadFile">Upload</v-btn>
         
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-app>
</template>

<script>
import axios from 'axios';
import JSEncrypt from 'jsencrypt';
import CryptoJS from 'crypto-js';

export default {
  data() {
    return {
      dialog: false,
      selectedFile: null,
      files: [],
      headers: [
        { text: 'Name', value: 'name' },
        // { text: 'Size', value: 'size' },
        { text: 'Actions', value: 'actions', sortable: false },
      ],
      userRole: localStorage.getItem("role"), // 从localStorage获取用户角色
      userstatus: localStorage.getItem("status"), // 从localStorage获取用户角色
    };
  },
  mounted() {
    this.fetchFiles();
    
  },
  methods: {
    download(file) {
      const user_status =localStorage.getItem("status")
      console.log(user_status)
      if(user_status==="0"){
        this.downloadFile(file)
        return
      }
      window.open(file.url, '_blank');
    },
    decryptDownloadFile(file) {
      const PRIVATE_KEY = `-----BEGIN PRIVATE KEY-----
MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDySf3VHbk81ukT
7PL/lNuqVHgFL2muW9mCCF6DqwwfporjstLg36BR/A87bzzferPuMVqCpSpJAXu5
9CuRp5lCB3yANF839CHmslOqwmlFeVoVvnuitdcVh9qvDmn1Ta2G4d47KL7ZVNWa
8RMulWx/ympdMnOJIg493YgMM2YPrW/Rh3+FRr8sqrkimWF+bnlzGoQMcj8nSS+J
KrNLtYIJ4x36o1Ml8SUoz0zuXOoL3O49vnA+tn6ItiPcduV9a6N7hHSOD9fEL4yS
Y84xpVDd8eK6rnUtUpiSANKuxIjPGNk6zkchPL+Lho3iTOz0bZ25hmrGtxg0JyqN
nJUwMWvDAgMBAAECggEAdunTt9YnxPFhYZMozEGd8iIU6c6UWqvfu88pvAumHp6Z
ihmJIC8BO1uviicVREWvq5bzai1v7Hba57Ar9gfA00RjWXTaytZ6EQSyxZs3GY7u
pL9hZMFEd9++d65mWKuwIAQZEwXzbS0SzUHGfVV+89U/kNAjHknlnX1tuTc8vzIW
tnd71zcLaXLzzpJhgkEEGc1IoW7DYfFMuPrhtXk7y+zLIWFbv+7GKDFRJ+e8KTqr
/swEGYshAmD9CRaqW3OGbLb65IOECZTDXFkUuWXSNjfiOYV38nVowq1NIYIQJxIH
JwsToCYfgZQciDvtOh6lZAV8bgG+gwFoPldAM0MfmQKBgQD7Klx2vPwJ1a8UuD7v
EVSPo5kyVr+AfT/CuZ5E1PFcyt7KMT4TsCWjOOSy4VrRHCyRpXwhBjI1uJV4hU4Q
qDIpJIal9F2esOvLdZMa6xQ1UVhn6HpOFj39FT2P8BA1SYaDECcyldr7z+63WsQG
eAZofSjbocbx7iItdODFl34etwKBgQD28+QT5T9YjuhfHtxuneKu6mtXTqJuLXUt
FsziucHZ5byNjSySMv3AdIBAV/Vm/4Ez/AIzamjejou41I4pTOmOE3z+jqFGII7G
pq9P1y1D+v1UUgTliGOvlUKKvkPea6XZpelJGH8MYGkL4Cd2ivQBgVSyfnukYJZP
Mf/ZCB+PVQKBgQCxr7pSVkiIPJ/sLJx5TO1h4P9UWYKJSBJ/lAmf8HYAi5UpvcSI
8SjvXCSPWFaDcUcmkshKJLQIxVkZNlWP+y3hZXHMniBNUCTAf6FefciCH9ZHTHSa
IaohDZHL7q3IxQdgWWEhrFqLowLivFfJq8f8y+7H0p+IMEwFlJYfs5kJ6QKBgQCA
qr4w54bDu2Gy/b9YGwcan6ThzmSvBxxAK9sAXkx0HVDKZ73LRqoTdh/EZo/D5GgL
D8iNxWlyW87MtGKFfj9J+Tls4B+DDD+XnQ1GihRZkRIgtsM6XH+j4h7TbyKpZmj5
J4qOvSak6i6RM28cQnWzuxDIF0KQeIqYJLLq/2KCKQKBgGmxU+ac7trUh3yAuY1t
DoJ1eicKAXCcYIGVs9+Me4WeeyTvZBxeqSYaone0G08JQT9xtCHSRoZPXnb0PL8U
6177XZfdqp6eFDh5kOXsyC9LQjEqYDFgQzRMwWY+yfpL6/o8Ibf5o3dS6me+pvU0
dk9pTm2dDpgZsz5d4tVkCVMp
-----END PRIVATE KEY-----`
  axios.get(file.url, { responseType: 'json' })
    .then(response => {
      const encryptedData = response.data;
      const decrypt = new JSEncrypt();
      decrypt.setPrivateKey(PRIVATE_KEY);
      const decryptedAESKey = decrypt.decrypt(encryptedData.key);
      const decryptedContent = CryptoJS.AES.decrypt(encryptedData.content, CryptoJS.enc.Utf8.parse(decryptedAESKey), {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
      }).toString(CryptoJS.enc.Utf8);

      const blob = new Blob([decryptedContent], { type: "text/plain" });
      const downloadUrl = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = downloadUrl;
      link.download = "decrypted_" + file.name;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    })
    .catch(error => console.error('Error downloading file:', error));
},
    fetchFiles() {
      // 请替换为你的文件列表 API
      axios.get('http://localhost:7529/api/filelist')
        .then(response => {
          this.files = response.data.map(file => ({
            ...file,
            size: 'Unknown' // 假设文件大小未知
          }));
        })
        .catch(error => console.error('Error fetching files:', error));
    },
    downloadFile(file) {
      const PUBLIC_KEY = `-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8kn91R25PNbpE+zy/5Tb
qlR4BS9prlvZggheg6sMH6aK47LS4N+gUfwPO28833qz7jFagqUqSQF7ufQrkaeZ
Qgd8gDRfN/Qh5rJTqsJpRXlaFb57orXXFYfarw5p9U2thuHeOyi+2VTVmvETLpVs
f8pqXTJziSIOPd2IDDNmD61v0Yd/hUa/LKq5Iplhfm55cxqEDHI/J0kviSqzS7WC
CeMd+qNTJfElKM9M7lzqC9zuPb5wPrZ+iLYj3HblfWuje4R0jg/XxC+MkmPOMaVQ
3fHiuq51LVKYkgDSrsSIzxjZOs5HITy/i4aN4kzs9G2duYZqxrcYNCcqjZyVMDFr
wwIDAQAB
-----END PUBLIC KEY-----`
  axios.get(file.url, { responseType: 'arraybuffer' })
    .then(response => {
      const AESKey = CryptoJS.lib.WordArray.random(16).toString(CryptoJS.enc.Hex);
      const wordArray = CryptoJS.lib.WordArray.create(response.data);
      const encryptedContent = CryptoJS.AES.encrypt(wordArray, CryptoJS.enc.Utf8.parse(AESKey), {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
      }).toString();

      const encrypt = new JSEncrypt();
      encrypt.setPublicKey(PUBLIC_KEY);
      const encryptedAESKey = encrypt.encrypt(AESKey);

      const encryptedData = JSON.stringify({
        key: encryptedAESKey,
        content: encryptedContent
      });

      const blob = new Blob([encryptedData], { type: "application/json" });
      const downloadUrl = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = downloadUrl;
      link.download = "encrypted_" + file.name;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    })
    .catch(error => console.error('Error downloading file:', error));
},
    handleFileUpload(event) {
      this.selectedFile = event.target.files[0];
    },
    uploadFile() {
      const formData = new FormData();
      formData.append('file', this.selectedFile);
      // 请替换为你的文件上传 API
      axios.post('http://localhost:7529/api/upload', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        },
      })
        .then(() => {
          this.dialog = false;
          this.fetchFiles();
          console.log('File uploaded successfully');
        })
        .catch(error => console.error('Error uploading file:', error));
    },

    handleEncryptedFileUpload(event) {
      const file = event.target.files[0];
      if (!file) {
        console.log("file")
        return;
      }
      const reader = new FileReader();
      reader.onload = (e) => {
        const encryptedData = JSON.parse(e.target.result);
        this.decryptFile(encryptedData, file.name);
      };
      reader.readAsText(file);
    },
    base64ToBlob(base64, contentType) {
    const sliceSize = 512;
    const byteCharacters = atob(base64);
    const byteArrays = [];

    for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
      const slice = byteCharacters.slice(offset, offset + sliceSize);

      const byteNumbers = new Array(slice.length);
      for (let i = 0; i < slice.length; i++) {
        byteNumbers[i] = slice.charCodeAt(i);
      }

      const byteArray = new Uint8Array(byteNumbers);
      byteArrays.push(byteArray);
    }

    return new Blob(byteArrays, {type: contentType});
  },
    decryptFile(encryptedData, fileName) {

     const PRIVATE_KEY = `-----BEGIN PRIVATE KEY-----
MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDySf3VHbk81ukT
7PL/lNuqVHgFL2muW9mCCF6DqwwfporjstLg36BR/A87bzzferPuMVqCpSpJAXu5
9CuRp5lCB3yANF839CHmslOqwmlFeVoVvnuitdcVh9qvDmn1Ta2G4d47KL7ZVNWa
8RMulWx/ympdMnOJIg493YgMM2YPrW/Rh3+FRr8sqrkimWF+bnlzGoQMcj8nSS+J
KrNLtYIJ4x36o1Ml8SUoz0zuXOoL3O49vnA+tn6ItiPcduV9a6N7hHSOD9fEL4yS
Y84xpVDd8eK6rnUtUpiSANKuxIjPGNk6zkchPL+Lho3iTOz0bZ25hmrGtxg0JyqN
nJUwMWvDAgMBAAECggEAdunTt9YnxPFhYZMozEGd8iIU6c6UWqvfu88pvAumHp6Z
ihmJIC8BO1uviicVREWvq5bzai1v7Hba57Ar9gfA00RjWXTaytZ6EQSyxZs3GY7u
pL9hZMFEd9++d65mWKuwIAQZEwXzbS0SzUHGfVV+89U/kNAjHknlnX1tuTc8vzIW
tnd71zcLaXLzzpJhgkEEGc1IoW7DYfFMuPrhtXk7y+zLIWFbv+7GKDFRJ+e8KTqr
/swEGYshAmD9CRaqW3OGbLb65IOECZTDXFkUuWXSNjfiOYV38nVowq1NIYIQJxIH
JwsToCYfgZQciDvtOh6lZAV8bgG+gwFoPldAM0MfmQKBgQD7Klx2vPwJ1a8UuD7v
EVSPo5kyVr+AfT/CuZ5E1PFcyt7KMT4TsCWjOOSy4VrRHCyRpXwhBjI1uJV4hU4Q
qDIpJIal9F2esOvLdZMa6xQ1UVhn6HpOFj39FT2P8BA1SYaDECcyldr7z+63WsQG
eAZofSjbocbx7iItdODFl34etwKBgQD28+QT5T9YjuhfHtxuneKu6mtXTqJuLXUt
FsziucHZ5byNjSySMv3AdIBAV/Vm/4Ez/AIzamjejou41I4pTOmOE3z+jqFGII7G
pq9P1y1D+v1UUgTliGOvlUKKvkPea6XZpelJGH8MYGkL4Cd2ivQBgVSyfnukYJZP
Mf/ZCB+PVQKBgQCxr7pSVkiIPJ/sLJx5TO1h4P9UWYKJSBJ/lAmf8HYAi5UpvcSI
8SjvXCSPWFaDcUcmkshKJLQIxVkZNlWP+y3hZXHMniBNUCTAf6FefciCH9ZHTHSa
IaohDZHL7q3IxQdgWWEhrFqLowLivFfJq8f8y+7H0p+IMEwFlJYfs5kJ6QKBgQCA
qr4w54bDu2Gy/b9YGwcan6ThzmSvBxxAK9sAXkx0HVDKZ73LRqoTdh/EZo/D5GgL
D8iNxWlyW87MtGKFfj9J+Tls4B+DDD+XnQ1GihRZkRIgtsM6XH+j4h7TbyKpZmj5
J4qOvSak6i6RM28cQnWzuxDIF0KQeIqYJLLq/2KCKQKBgGmxU+ac7trUh3yAuY1t
DoJ1eicKAXCcYIGVs9+Me4WeeyTvZBxeqSYaone0G08JQT9xtCHSRoZPXnb0PL8U
6177XZfdqp6eFDh5kOXsyC9LQjEqYDFgQzRMwWY+yfpL6/o8Ibf5o3dS6me+pvU0
dk9pTm2dDpgZsz5d4tVkCVMp
-----END PRIVATE KEY-----`

        // 使用私钥解密AES密钥
  const decrypt = new JSEncrypt();
  decrypt.setPrivateKey(PRIVATE_KEY);
  const decryptedAESKey = decrypt.decrypt(encryptedData.key);

  // 使用AES密钥解密文件内容
  const decryptedContent = CryptoJS.AES.decrypt(encryptedData.content, CryptoJS.enc.Utf8.parse(decryptedAESKey), {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7
  });

  // 转换为Base64，避免UTF-8解码错误
  const decryptedText = CryptoJS.enc.Base64.stringify(decryptedContent);

  // 将Base64字符串转换为Blob对象
  const blob = this.base64ToBlob(decryptedText, 'application/octet-stream');
  const downloadUrl = URL.createObjectURL(blob);
  const downloadLink = document.createElement('a');
  downloadLink.href = downloadUrl;
  downloadLink.download = `decrypted_${fileName}`;
  document.body.appendChild(downloadLink);
  downloadLink.click();
  document.body.removeChild(downloadLink);
    },
  
  }
};
</script>

<style scoped>
</style>
