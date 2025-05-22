import { NativeModules } from 'react-native';

const { TextRecognitionModule } = NativeModules;

export const recognizeText = async (imagePath) => {
  try {
    const result = await TextRecognitionModule.recognizeText(imagePath);
    return result;
  } catch (error) {
    console.error('Error recognizing text:', error);
    return 'Error recognizing text.';
  }
};
