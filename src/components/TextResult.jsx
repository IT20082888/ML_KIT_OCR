import React from 'react';
import { View, Text, StyleSheet } from 'react-native';

const TextResult = ({ text }) => {
  return (
    <View style={styles.container}>
      <Text style={styles.text}>{text || 'No text recognized yet.'}</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
  },
  text: {
    fontSize: 16,
    textAlign: 'center',
  },
});

export default TextResult;
