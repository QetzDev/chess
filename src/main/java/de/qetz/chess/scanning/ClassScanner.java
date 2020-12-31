package de.qetz.chess.scanning;

import com.google.common.base.Preconditions;

import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;
import java.util.Collection;

public final class ClassScanner {
  private final ClassInfoList result;

  private ClassScanner(ClassInfoList result) {
    this.result = result;
  }

  public Stream<Class<?>> findNonAnnotated(Annotation annotation) {
    return result
      .filter(classInfo -> !classInfo
        .hasAnnotation(annotation.getClass().getName()))
      .loadClasses().stream();
  }

  public Stream<Class<?>> findAnnotated(Annotation annotation) {
    return result
      .filter(classInfo -> classInfo
        .hasAnnotation(annotation.getClass().getName()))
      .loadClasses().stream();
  }

  public Stream<Class<?>> findAnnotated(
    Class<? extends Annotation> annotationType
  ) {
    return result
      .filter(classInfo -> classInfo.hasAnnotation(annotationType.getName()))
      .loadClasses().stream();
  }

  public Stream<Class<?>> findNonAnnotated(
    Class<? extends Annotation> annotationType
  ) {
    return result
      .filter(classInfo -> !classInfo.hasAnnotation(annotationType.getName()))
      .loadClasses().stream();
  }

  public <E> Stream<Class<E>> findSubTypes(Class<E> superType) {
    return result
      .filter(classInfo -> classInfo.extendsSuperclass(superType.getName()))
      .stream()
      .map(this::unsafeTypeCast);
  }

  @SuppressWarnings("unchecked")
  private <E> Class<E> unsafeTypeCast(ClassInfo classInfo) {
    return (Class<E>) classInfo.loadClass();
  }

  public Stream<Class<?>> classes() {
    return result.loadClasses().stream();
  }

  public static ClassScanner create() {
    return new ClassScanner(new ClassGraph()
      .enableClassInfo()
      .enableAnnotationInfo()
      .scan()
      .getAllClasses());
  }

  public static ClassScanner createInPackage(String packageName) {
    return new ClassScanner(new ClassGraph()
      .enableClassInfo()
      .enableAnnotationInfo()
      .whitelistPackagesNonRecursive(packageName)
      .scan()
      .getAllClasses());
  }

  public static ClassScanner createInPackageRecursive(String packageName) {
    return new ClassScanner(new ClassGraph()
      .enableClassInfo()
      .enableAnnotationInfo()
      .whitelistPackages(packageName)
      .scan()
      .getAllClasses());
  }

  public static ClassScanner of(Collection<Class<?>> classes) {
    Preconditions.checkNotNull(classes);
    return new ClassScanner(new ClassGraph()
      .enableClassInfo()
      .enableAnnotationInfo()
      .whitelistClasses(classes.stream()
        .map(Class::getName)
        .toArray(String[]::new))
      .scan()
      .getAllClasses());
  }
}