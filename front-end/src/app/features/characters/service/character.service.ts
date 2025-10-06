import { Injectable } from '@angular/core';
import { CharacterListItem } from '../list-items/character-list-item';
import { Observable } from 'rxjs';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Sort } from '../../../core/sort';
import { Filter } from '../filter';
import { Character } from '../interfaces/character';

@Injectable({
  providedIn: 'root'
})
export class CharacterService {
  readonly url: string='http://localhost:8080/api/characters';

  constructor(private httpClient:HttpClient) { }

  getAllDeleted(sort: Sort, filter: Filter, email:string):Observable<HttpResponse<CharacterListItem[]>> {
    return this.httpClient
      .post<CharacterListItem[]>(
        this.url+'/getForUser/deleted/'+email,{
          filter:filter,
          sort:sort
        },
        {observe:'response'}
      );
  }

  getAll(sort: Sort, filter: Filter, email:string):Observable<HttpResponse<CharacterListItem[]>> {
    return this.httpClient
      .post<CharacterListItem[]>(
        this.url+'/getForUser/'+email,{
          filter:filter,
          sort:sort
        },
        {observe:'response'}
      );
  }

  public getById(id:number): Observable<HttpResponse<Character>>{
    return this.httpClient
      .get<Character>(this.url+'/'+id,{observe:'response'});
  }

  public create(character: Character):Observable<Character>{
    return this.httpClient.post<Character>(this.url, character);
  }

  public edit(character: Character):Observable<Character>{
    return this.httpClient.put<Character>(this.url, character);
  }

  public delete(id:number):Observable<Character>{
    return this.httpClient.delete<Character>(this.url+"?id="+id);
  }

  public confirmedDelete(id:number):Observable<Character>{
    return this.httpClient.delete<Character>(this.url+"/confirmedDelete?id="+id);
  }

  public restore(id:number):Observable<Character>{
    return this.httpClient.put<Character>(this.url+"/restore/"+id,null);
  }
}
