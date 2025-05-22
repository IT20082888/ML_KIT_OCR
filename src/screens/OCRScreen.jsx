import React, { useState } from 'react';
import { View, StyleSheet } from 'react-native';
import CameraInput from '../components/CameraInput';
import GalleryInput from '../components/GalleryInput';
import TextResult from '../components/TextResult';
import { recognizeText } from '../utils/mlkitHelper';

const OCRScreen = ({ route }) => {
  const { mode } = route.params;
  const [recognizedText, setRecognizedText] = useState('');

  const handleImage = async (imageUri) => {
    const text = await recognizeText(imageUri);
    setRecognizedText(text);
  };

  return (
    <View style={styles.container}>
      {mode === 'camera' && <CameraInput onImageCaptured={handleImage} />}
      {mode === 'gallery' && <GalleryInput onImageSelected={handleImage} />}
      <TextResult text={recognizedText} />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default OCRScreen;
