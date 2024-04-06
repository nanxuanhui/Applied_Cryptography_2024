<template>
    <v-container>
      <!-- FileDownload Card -->
      <v-card class="ma-5" outlined>
        <v-card-title>File Download</v-card-title>
        <v-card-text>
          Click the button below to download files.
        </v-card-text>
        <v-card-actions>
          <v-btn color="primary" @click="goToFileDownload">Go to Download</v-btn>
        </v-card-actions>
      </v-card>
  
      <!-- UserMember Card -->
      <v-card class="ma-5" outlined v-if="userRole === '1'">
        <v-card-title>User Membership</v-card-title>
        <v-card-text>
          Click the button below to view user membership information.
        </v-card-text>
        <v-card-actions>
          <v-btn color="primary" @click="goToUserMember">View Membership</v-btn>
        </v-card-actions>
      </v-card>
    </v-container>
  </template>
  
  <script>
  export default {
    data() {
    return {
     
     
      userRole: localStorage.getItem("role"), // 从localStorage获取用户角色
     
    };
  },
    methods: {
      goToFileDownload() {
        this.$router.push('/fileDownload');
      },
      goToUserMember() {
        this.$router.push('/user');
      },
    },
  };
  </script>
  