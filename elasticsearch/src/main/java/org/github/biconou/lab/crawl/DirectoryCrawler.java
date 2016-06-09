package org.github.biconou.lab.crawl;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by remi on 17/05/2016.
 */
public class DirectoryCrawler {


 /* private MediaFile createMediaFile(File file) {

    MediaFile mediaFile = new MediaFile();
    Date lastModified = new Date(FileUtil.lastModified(file));
    mediaFile.setPath(file.getPath());
    mediaFile.setFolder(securityService.getRootFolderForFile(file));
    mediaFile.setParentPath(file.getParent());
    mediaFile.setChanged(lastModified);
    mediaFile.setLastScanned(new Date());
    mediaFile.setPlayCount(0);
    mediaFile.setLastPlayed(null);
    mediaFile.setComment(null);
    mediaFile.setChildrenLastUpdated(new Date(0));
    mediaFile.setCreated(lastModified);
    mediaFile.setMediaType(DIRECTORY);
    mediaFile.setPresent(true);

    if (file.isFile()) {

      MetaDataParser parser = metaDataParserFactory.getParser(file);
      if (parser != null) {
        MetaData metaData = parser.getMetaData(file);
        mediaFile.setArtist(metaData.getArtist());
        mediaFile.setAlbumArtist(metaData.getAlbumArtist());
        mediaFile.setAlbumName(metaData.getAlbumName());
        mediaFile.setTitle(metaData.getTitle());
        mediaFile.setDiscNumber(metaData.getDiscNumber());
        mediaFile.setTrackNumber(metaData.getTrackNumber());
        mediaFile.setGenre(metaData.getGenre());
        mediaFile.setYear(metaData.getYear());
        mediaFile.setDurationSeconds(metaData.getDurationSeconds());
        mediaFile.setBitRate(metaData.getBitRate());
        mediaFile.setVariableBitRate(metaData.getVariableBitRate());
        mediaFile.setHeight(metaData.getHeight());
        mediaFile.setWidth(metaData.getWidth());
      }
      String format = StringUtils.trimToNull(StringUtils.lowerCase(FilenameUtils.getExtension(mediaFile.getPath())));
      mediaFile.setFormat(format);
      mediaFile.setFileSize(FileUtil.length(file));
      mediaFile.setMediaType(getMediaType(mediaFile));

    } else {

      // Is this an album?
      if (!isRoot(mediaFile)) {
        File[] children = FileUtil.listFiles(file);
        File firstChild = null;
        for (File child : filterMediaFiles(children)) {
          if (FileUtil.isFile(child)) {
            firstChild = child;
            break;
          }
        }

        if (firstChild != null) {
          mediaFile.setMediaType(ALBUM);

          // Guess artist/album name, year and genre.
          MetaDataParser parser = metaDataParserFactory.getParser(firstChild);
          if (parser != null) {
            MetaData metaData = parser.getMetaData(firstChild);
            mediaFile.setArtist(metaData.getAlbumArtist());
            mediaFile.setAlbumName(metaData.getAlbumName());
            mediaFile.setYear(metaData.getYear());
            mediaFile.setGenre(metaData.getGenre());
          }

          // Look for cover art.
          try {
            File coverArt = findCoverArt(children);
            if (coverArt != null) {
              mediaFile.setCoverArtPath(coverArt.getPath());
            }
          } catch (IOException x) {
            LOG.error("Failed to find cover art.", x);
          }

        } else {
          mediaFile.setArtist(file.getName());
        }
      }
    }
    return mediaFile;
  } */

  public static void main(String args[]) {

    Path path = Paths.get("C:\\biconouSubsonicElasticSearch\\subsonic-main\\src\\test\\resources\\MEDIAS");
    //final List<Path> files = new ArrayList<>();
    try

    {
      Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          if (attrs.isDirectory()) {
            System.out.print("D - ");
          }

          System.out.println(file.getName(0));
          return FileVisitResult.CONTINUE;
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
