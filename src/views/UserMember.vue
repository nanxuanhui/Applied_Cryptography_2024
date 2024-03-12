<template>
    <v-app>
      <v-container>
        <v-row>
          <v-col cols="12">
            <v-card>
              <v-card-title>User Management</v-card-title>
              <v-card-text>
                <v-divider class="mt-4"></v-divider>
                <v-data-table
                  :headers="headers"
                  :items="users"
                  :items-per-page="5"
                  class="elevation-1"
                >
                  <template v-slot:item.actions="{ item }">
                    <v-checkbox v-model="item.schoolMember">School</v-checkbox>
                    <v-checkbox v-model="item.nonSchoolMember">Non-school</v-checkbox>
                  </template>
                </v-data-table>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-app>
  </template>
  
  <script>
  export default {
    data() {
      return {
        users: [
          { id: 1, name: 'John Doe', account: 'johndoe', schoolMember: false, nonSchoolMember: false },
          { id: 2, name: 'Jane Smith', account: 'janesmith', schoolMember: false, nonSchoolMember: false },
          { id: 3, name: 'Michael Johnson', account: 'michaeljohnson', schoolMember: false, nonSchoolMember: false },
          { id: 4, name: 'Emily Davis', account: 'emilydavis', schoolMember: false, nonSchoolMember: false },
        ],
        headers: [
          { text: 'Name', value: 'name' },
          { text: 'Account', value: 'account' },
          { text: 'School Member', value: 'schoolMember', sortable: false },
          { text: 'Non-school Member', value: 'nonSchoolMember', sortable: false },
        ],
      };
    }
  };
  </script>
  
  <style scoped>

  </style>
  