<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
   
    <ul>
      <li>Enter data to encrypt:</li>
      <li class="flex">
        <div class="key">Base64:</div>
        <div class="val">
          <input type="text" v-model="params.name" />
        </div>
      </li>
      <li class="flex">
        <div class="key"> Base64:</div>
        <div class="val">
          <input type="text" v-model="params.idcard" />
        </div>
      </li>
      <li class="flex">
        <div class="key">Base64:</div>
        <div class="val">
          <input type="text" v-model="params.phone" />
        </div>
      </li>
      <li class="flex">
        <div class="key">Base64:</div>
        <div class="val">
          <input type="text" v-model="params.other" />
        </div>
      </li>
    </ul>
    <button @click="encryptData" class="btn">Generate Encrypted Data</button>
    <div v-if="encryptedArr.length > 0">
      <ul>
        <li>Encrypted Data:</li>
        <li v-for="(item, index) in encryptedArr" :key="index" class="flex">
          <div class="key">{{item.key}}:</div>
          <div class="val">{{item.val}}</div>
        </li>
      </ul>
    </div>
    <button @click="decryptData" class="btn">Decrypt Data</button>
    <div class="text-align padding" v-show="decryptedStatus">
      <span style="color: green;">Signature Verification Successful</span>
    </div>
    <div v-if="decryptedArr.length > 0">
      <ul>
        <li>Decrypted Data:</li>
        <li v-for="(item, index) in decryptedArr" :key="index" class="flex">
          <div class="key">{{item.key}}:</div>
          <div class="val">{{item.val}}</div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import Encrypt from "@/assets/encrypt.js";
import Decrypt from "@/assets/decrypt.js";
export default {
  name: "HelloWorld",
  data() {
    return {
      params: {
        name: "base64filedata",
        idcard: "base64filedata",
        phone: "base64filedata",
        other: ""
      },
      // Encrypted Data
      encrypted: null,
      encryptedArr: [],
      // Decrypted Data
      decrypted: null,
      decryptedArr: [],
      decryptedStatus: false
    };
  },
  props: {
    msg: String
  },
  methods: {
    // Encrypt Data, usually placed in request interceptors
    encryptData() {
      const encryptor = new Encrypt(this.params);
      // data is the encrypted data
      let data = encryptor.generateData();
      // ======
      this.encrypted = { ...data };
      this.encryptedArr = this.getKeyValArr(data);
    },
    // Decrypt Data, usually placed in response interceptors
    decryptData() {
      const decryptor = new Decrypt(
        this.encrypted.encryptedKey,
        this.encrypted.encryptedData
      );
      // Check if the signature is valid
      const ispass = decryptor.isPass(
        {
          clientID: '',
          sequenceNo: this.encrypted.sequenceNo,
          timestamp: this.encrypted.timestamp,
          version: this.encrypted.version
        },
        this.encrypted.signature
      );
      // If the signature is valid
      if (ispass) {
        this.decryptedStatus = ispass;
        // data is the decrypted data
        try {
          let data = JSON.parse(decryptor.decrypted).body;
          this.decryptedArr = this.getKeyValArr(data);
          console.log(data);
        } catch (err) {
          console.log(err);
        }
      } else {
        alert('Signature Verification Failed')
      }
    },
    /**
     * @param {Object} obj
     * @return {Array} [{key:val}, ...]
     * **/
    getKeyValArr(obj) {
      let arr = [];
      let keys = Object.keys(obj);
      for (let key of keys) {
        arr.push({
          key: key,
          val: obj[key]
        });
      }
      return arr;
    }
  }
};
</script>


<style scoped >
.text-center {
  text-align: center;
}
.margin {
  margin: 15px;
}
.padding {
  padding: 15px;
}
.btn {
  display: block;
  margin: 20px auto;
  font-size: 16px;
  background: cornflowerblue;
  color: #fff;
  outline: none;
  border: none;
  padding: 4px 10px;
  border-radius: 10px;
}
.flex {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 4px 12px;
}
.flex .key {
  font-weight: bold;
  font-size: 14px;
  min-width: 5em;
  text-align: left;
}
.flex .val {
  flex: 1;
  flex-grow: 1;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  color: #f00;
  font-size: 12px;
}
.flex .val input {
  width: 100%;
  font-size: 14px;
}

</style>
