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
                  <v-radio-group v-model="item.userStatus" @change="updateUserStatus(item)" row>
                    <v-radio :label="'School'" :value="1"></v-radio>
                    <v-radio :label="'Non-school'" :value="0"></v-radio>
                  </v-radio-group>
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
import axios from 'axios';

export default {
  data() {
    return {
      users: [],
      headers: [
        { text: 'Name', value: 'name' },
        { text: 'Account', value: 'account' },
        { text: 'Membership', value: 'actions', sortable: false },
      ],
    };
  },
  mounted() {
    this.fetchUsers();
  },
  methods: {
    fetchUsers() {
      axios.get('http://localhost:7529/api/user/search')
        .then(response => {
          this.users = response.data.data.map(user => ({
            ...user,
            name: user.userAccount, // Assuming you want to use userAccount as name for display
            account: user.userAccount,
            userStatus: user.userStatus,
          }));
        })
        .catch(error => {
          console.error('Error fetching users:', error);
        });
    },
    updateUserStatus(item) {
      const apiUrl = item.userStatus === 1 ? 'http://localhost:7529/api/user/status1' : 'http://localhost:7529/api/user/status0';
      axios.post(apiUrl, { id: item.id })
        .then(() => {
          this.fetchUsers(); // Refresh data
        })
        .catch(error => {
          console.error('Error updating user status:', error);
        });
    }
  }
};
</script>

<style scoped>
</style>
