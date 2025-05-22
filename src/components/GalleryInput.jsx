import React, { useEffect } from 'react';
import { View, StyleSheet } from 'react-native';
import { launchImageLibrary } from 'react-native-image-picker';

const GalleryInput = ({ onImageSelected }) => {
  useEffect(() => {
    launchImageLibrary({ mediaType: 'photo' }, (response) => {
      if (response.assets && response.assets.length > 0) {
        onImageSelected(response.assets[0].uri);
      }
    });
  }, [onImageSelected]);

  return <View style={styles.container} />;
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default GalleryInput;
