import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GenreDeleteComponent } from './genre-delete.component';

describe('GenreDeleteComponent', () => {
  let component: GenreDeleteComponent;
  let fixture: ComponentFixture<GenreDeleteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GenreDeleteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GenreDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
