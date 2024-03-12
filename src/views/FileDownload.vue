<template>
    <v-app>
      <v-container>
        <v-row>
          <v-col cols="12">
            <v-card>
              <v-card-title>File Download Selection</v-card-title>
              <v-card-text>
                <v-data-table
                  :headers="headers"
                  :items="files"
                  :items-per-page="5"
                  class="elevation-1"
                >
                  <template v-slot:item.actions="{ item }">
                    <v-btn color="primary" @click="downloadFile(item)">Download</v-btn>
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
        files: [
          { id: 1, name: 'File 1', size: '10 MB', url: 'https://example.com/file1.pdf' },
          { id: 2, name: 'File 2', size: '5 MB', url: 'https://example.com/file2.zip' },
          { id: 3, name: 'File 3', size: '2 MB', url: 'https://example.com/file3.docx' },
          { id: 4, name: 'File 4', size: '8 MB', url: 'https://example.com/file4.jpg' },
        ],
        headers: [
          { text: 'Name', value: 'name' },
          { text: 'Size', value: 'size' },
          { text: 'Actions', value: 'actions', sortable: false },
        ],
      };
    },
    methods: {
      downloadFile(file) {
        // Perform file download logic here
        window.open(file.url, '_blank');
      }
    }
  };
  </script>
  
  <style scoped>

  </style>
  