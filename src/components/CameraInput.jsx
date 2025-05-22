import React, { useEffect } from 'react';
import { View, StyleSheet } from 'react-native';
import { launchCamera } from 'react-native-image-picker';

const CameraInput = ({ onImageCaptured }) => {
  useEffect(() => {
    launchCamera({ mediaType: 'photo' }, (response) => {
      if (response.assets && response.assets.length > 0) {
        onImageCaptured(response.assets[0].uri);
      }
    });
  }, [onImageCaptured]);

  return <View style={styles.container} />;
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default CameraInput;
