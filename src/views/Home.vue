<template>
  <v-container>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="10">
        <v-card class="elevation-6 mt-10">
          <v-window v-model="step">
            <v-window-item :value="1">
              <v-row>
                <v-col cols="12" md="6">
                  <v-card-text class="mt-12">
                    <h4 class="text-center">Login in to Your Account</h4>
                    <h6 class="text-center grey--text">Once logged in, you can download the contents of the school's files and view them</h6>
                    <v-row align="center" justify="center">
                      <v-col cols="12" sm="8">
                        <v-text-field v-model="login.userAccount" label="Account" outlined dense color="blue" autocomplete="false" class="mt-16"/>
                        <v-text-field v-model="login.userPassword" label="Password" outlined dense color="blue" autocomplete="false" type="password"/>
                        <v-row>
                          <v-col cols="12" sm="7">
                            <v-checkbox v-model="rememberMe" label="Remember Me" class="mt-n1" color="blue"></v-checkbox>
                          </v-col>
                          <v-col cols="12" sm="5">
                            <span class="caption blue--text">Forgot password</span>
                          </v-col>
                        </v-row>
                        <v-btn @click="submitLogin" color="blue" dark block tile>Log in</v-btn>
                      </v-col>
                    </v-row>  
                  </v-card-text>
                </v-col>
                <v-col cols="12" md="6" class="blue rounded-bl-xl">
                  <div style="text-align: center; padding: 180px 0;">
                    <v-card-text class="white--text">
                      <h3 class="text-center">Don't Have an Account Yet?</h3>
                      <h6 class="text-center">Log in to view the school's file contents</h6>
                    </v-card-text>
                    <div class="text-center">
                      <v-btn tile outlined dark @click="step++">SIGN UP</v-btn>
                    </div>
                  </div>
                </v-col>
              </v-row>
            </v-window-item>
            <v-window-item :value="2">
              <v-row>
                <v-col cols="12" md="6" class="blue rounded-br-xl">
                  <div style="text-align: center; padding: 180px 0;">
                    <v-card-text class="white--text">
                      <h3 class="text-center">Already Signed up?</h3>
                      <h6 class="text-center">Already have an account? Once logged in, you can download the contents of the school's files and view them</h6>
                    </v-card-text>
                    <div class="text-center">
                      <v-btn tile outlined dark @click="step--">Log in</v-btn>
                    </div>
                  </div>
                </v-col>
                <v-col cols="12" md="6">
                  <v-card-text class="mt-12">
                    <h4 class="text-center">Sign Up for an Account</h4>
                    <h6 class="text-center grey--text">Log in to view the contents of the school file</h6>
                    <v-row align="center" justify="center">
                      <v-col cols="12" sm="8">
                        <!-- <v-row>
                          <v-col cols="12" sm="6">
                            <v-text-field v-model="register.firstName" label="First Name" outlined dense color="blue" autocomplete="false" class="mt-4"/>
                          </v-col>
                          <v-col cols="12" sm="6">
                            <v-text-field v-model="register.lastName" label="Last Name" outlined dense color="blue" autocomplete="false" class="mt-4"/>
                          </v-col>
                        </v-row> -->
                        <v-text-field v-model="register.userAccount" label="Account" outlined dense color="blue" autocomplete="false"/>
                        <v-text-field v-model="register.userPassword" label="Password" outlined dense color="blue" autocomplete="false" type="password"/>
                        <v-text-field v-model="register.checkPassword" label="Confirm Password" outlined dense color="blue" autocomplete="false" type="password"/>
                        <v-row>
                          <v-col cols="12" sm="7">
                            <v-checkbox v-model="acceptAAE" label="I Accept AAE" class="mt-n1" color="blue"></v-checkbox>
                          </v-col>
                          <v-col cols="12" sm="5">
                            <span class="caption blue--text ml-n4">School member</span>
                          </v-col>
                        </v-row>
                        <v-btn @click="submitRegister" color="blue" dark block tile>Sign up</v-btn>
                      </v-col>
                    </v-row>  
                  </v-card-text>
                </v-col>
              </v-row>
            </v-window-item>
          </v-window>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios'

export default {
  data: () => ({
    step: 1,
    login: {
      userAccount: '',
      userPassword: '',
    },
    register: {
      firstName: '',
      lastName: '',
      userAccount: '',
      userPassword: '',
      checkPassword: ''
    },
    rememberMe: false,
    acceptAAE: false,
  }),
  methods: {
    submitLogin() {
      axios.post('http://localhost:7529/api/user/login', {
        userAccount: this.login.userAccount,
        userPassword: this.login.userPassword,
      })
      .then(response => {
        console.log('Login success:', response);
        localStorage.setItem("token",response.data.data.id)
        localStorage.setItem("status",response.data.data.userStatus)
        localStorage.setItem("role",response.data.data.userRole)
        console.log(response.data.data.id)
        this.$router.push('/menu');
        window.alert('Login success')
        // Handle login success, e.g., redirect or show a success message
      })
      .catch(error => {
        console.error('Login error:', error);
        // Handle login failure, e.g., show an error message
      });
    },
    submitRegister() {
      axios.post('http://localhost:7529/api/user/register', {
        userAccount: this.register.userAccount,
        userPassword: this.register.userPassword,
        checkPassword: this.register.checkPassword,
      })
      .then(response => {
        console.log('Registration success:', response);
        window.alert("Registration success")
        // Handle registration success, e.g., redirect or show a success message
      })
      .catch(error => {
        console.error('Registration error:', error);
        // Handle registration failure, e.g., show an error message
      });
    }
  }
}
</script>

<style scoped>
.v-application .rounded-bl-xl {
    border-bottom-left-radius: 300px !important;
}
.v-application .rounded-br-xl {
    border-bottom-right-radius: 300px !important;
}
</style>
